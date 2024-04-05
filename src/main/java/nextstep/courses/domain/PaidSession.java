package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUsers;

public class PaidSession extends Session {
    private final int maxSize;
    private final long amount;

    public PaidSession(Course course, Period period, Image image, NsUsers users, int maxSize, long amount) {
        super(course, period, image, users,Type.PAID);
        this.maxSize = maxSize;
        this.amount = amount;
    }

    public PaidSession(Long idx, Course course, Period period, Image image, Status status, NsUsers nsUsers, int maxSize, long amount) {
        super(idx, course, period, image, status, nsUsers, Type.PAID);
        this.maxSize = maxSize;
        this.amount = amount;
    }

    @Override
    public void assertCanEnroll() {
        if (!nsUsers.isSmallerThanMaxSize(maxSize)) {
            throw new IllegalArgumentException("정원이 꽉 찼습니다. 다음에 이용해주세요.");
        }
    }

    public Long getAmount() {
        return this.amount;
    }
}
