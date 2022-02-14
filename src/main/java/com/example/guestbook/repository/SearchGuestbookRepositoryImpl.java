package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.entity.QMember;
import com.example.guestbook.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchGuestbookRepositoryImpl extends QuerydslRepositorySupport implements SearchGuestbookRepository {
    public SearchGuestbookRepositoryImpl(){
        super(Guestbook.class);
    }

    @Override
    public Guestbook search1(){
        QGuestbook guestbook = QGuestbook.guestbook;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Guestbook> jpqlQuery = from(guestbook);
        jpqlQuery.leftJoin(member).on(guestbook.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.guestbook.eq(guestbook));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(guestbook, member.email, reply.count()).groupBy(guestbook);
        tuple.groupBy(guestbook);
        List<Tuple> result = tuple.fetch();
        log.info(result);
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable){
        QGuestbook guestbook = QGuestbook.guestbook;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Guestbook> jpqlQuery = from(guestbook);
        jpqlQuery.leftJoin(member).on(guestbook.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.guestbook.eq(guestbook));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(guestbook, member, reply.count());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression booleanExpression = guestbook.gno.gt(0L);

        booleanBuilder.and(booleanExpression);

        if (type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t :typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(guestbook.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(guestbook.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
       tuple.where(booleanBuilder);
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Guestbook.class, "guestbook");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));

        });
        tuple.groupBy(guestbook);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();


        long count = tuple.fetchCount();

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count);
    }
}
