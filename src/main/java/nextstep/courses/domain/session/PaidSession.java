package nextstep.courses.domain.session;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.session.type.SessionType;
import nextstep.courses.domain.session.type.SessionStatus;
import nextstep.courses.domain.session.user.SessionUsers;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;

import java.util.List;

public class PaidSession extends Session {
    private final int maxSize;
    private final long amount;

    public PaidSession(Course course, String title, Period period, List<Image> images, SessionUsers users, int maxSize, long amount) {
        super(course, title, period, images, users, SessionType.PAID);
        this.maxSize = maxSize;
        this.amount = amount;
    }

    public PaidSession(Long idx, String title, Course course, Period period, List<Image> images, SessionStatus status, SessionUsers nsUsers, int maxSize, long amount, long creatorId) {
        super(idx, title, course, period, images, status, nsUsers, SessionType.PAID, creatorId);
        this.maxSize = maxSize;
        this.amount = amount;
    }

    @Override
    public void assertCanEnroll() {
        if (!sessionUsers.isSmallerThanMaxSize(maxSize)) {
            throw new IllegalArgumentException("정원이 꽉 찼습니다. 다음에 이용해주세요.");
        }
    }

    public Long getAmount() {
        return this.amount;
    }

    public Payment toPayment(NsUser nsUser) {
        return new Payment("0", this.id, nsUser.getId(), this.amount);
    }
}
