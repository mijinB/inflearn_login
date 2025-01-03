package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();       // static 사용
    private static long sequence = 0;                               // static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {     // Optional 은 Member 가 있을 수도 있고 없을 수도 있는 껍데기 통이다.
        /* List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
        ⇓ 아래 Java8 람다 표현식을 쓰면 간략하게 작성할 수 있다. */

        return findAll().stream()                                   // stream = List loop 를 돈다고 생각하면 된다.
                .filter(m -> m.getLoginId().equals(loginId))        // filter = true 인 것만 다음 단계로 넘어가고 false 이면 버려진다.
                .findFirst();
    }

    public List<Member> findAll() {
        return  new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
