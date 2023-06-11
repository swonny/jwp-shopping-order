package cart.domain.repository;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.domain.member.Member;
import cart.domain.vo.Cash;
import cart.domain.vo.Point;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final MemberDao memberDao;

    public JdbcMemberRepository(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void updatePoint(long memberId, int updatePoint) {
        memberDao.updatePoint(memberId, updatePoint);
    }

    @Override
    public Optional<Member> getMemberById(long memberId) {
        try {
            MemberEntity memberEntity = memberDao.getMemberById(memberId);
            return Optional.of(toMember(memberEntity));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateMoney(long memberId, int money) {
        memberDao.updateMoney(memberId, money);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        try {
            MemberEntity memberEntity = memberDao.getMemberByEmail(email);
            return Optional.of(toMember(memberEntity));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private Member toMember(MemberEntity memberEntity) {
        return new Member(
                memberEntity.getId(),
                memberEntity.getEmail(),
                memberEntity.getPassword(),
                new Point(memberEntity.getPoint()),
                new Cash(memberEntity.getMoney())
        );
    }
}
