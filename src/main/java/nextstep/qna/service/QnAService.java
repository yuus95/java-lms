package nextstep.qna.service;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.domain.*;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.AnswerRepository;
import nextstep.qna.domain.deleteHistory.DeleteHistories;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("qnaService")
public class QnAService {
    @Resource(name = "questionRepository")
    private QuestionRepository questionRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    @Transactional
    public void deleteQuestion(NsUser loginUser, long questionId) throws CannotDeleteException {
        LocalDateTime regDateTime = LocalDateTime.now();
        Question question = questionRepository.findById(questionId).orElseThrow(NotFoundException::new);

        question.delete(loginUser);
        DeleteHistories deleteHistories = question.getDeleteHistories(regDateTime);
        deleteHistoryService.saveAll(deleteHistories.getList());
    }

}
