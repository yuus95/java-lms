package nextstep.courses.domain.session;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.session.type.SessionType;
import nextstep.courses.domain.session.type.SessionStatus;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUsers;

import java.time.LocalDate;
import java.util.List;

public abstract class Session {
    protected final Long id;
    protected final String title;
    private final Course course;
    private final Period period;
    private final Image image;
    private final SessionStatus status;
    private final SessionType type;
    protected final NsUsers nsUsers;

    public Session(Course course, String title, Period period, Image image, NsUsers users, SessionType type) {
        this(0L, title, course, period, image, SessionStatus.READY, users, type);
    }

    public Session(Long id, String title, Course course, Period period, Image image, SessionStatus status, NsUsers nsUsers, SessionType type) {
        this.id = id;
        this.title = title;
        this.course = course;
        this.period = period;
        this.image = image;
        this.status = status;
        this.nsUsers = nsUsers;
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void enroll(NsUser nsUser, LocalDate date) {
        if (!canEnrollStatus()) {
            throw new IllegalArgumentException("강의는 현재 모집하고 있지 않습니다.");
        }
        if (period.isStart(date)) {
            throw new IllegalArgumentException("강의가 시작되어 수강신청을 할 수 없습ㄴ디ㅏ.");
        }
        assertCanEnroll();
        nsUsers.add(nsUser);
    }

    public abstract void assertCanEnroll();

    private boolean canEnrollStatus() {
        return this.status == SessionStatus.RECRUITING;
    }

    private boolean equalsType(SessionType type) {
        if (type == null) {
            return false;
        }
        return this.type == type;
    }

    public boolean isFreeSession() {
        return equalsType(SessionType.FREE);
    }

    public Payment toPayment(NsUser nsUser) {
        if (isFreeSession()) {
            throw new IllegalArgumentException("무료강의는 결제를 할 수 없습니다.");
        }
        PaidSession paidSession = (PaidSession) this;
        return paidSession.toPayment(nsUser);
    }

    public void addNsUser(List<NsUser> nsUsers) {
        this.nsUsers.addAll(nsUsers);
    }
}
