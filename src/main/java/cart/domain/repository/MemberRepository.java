package cart.domain.repository;

import cart.domain.member.Member;

public interface MemberRepository {

    Member getMemberById(long memberId);

    void updateMoney(long memberId, int money);

    Member getMemberByEmail(String email);
}
