// 한국인이면서 배씨성을 가진 사람중에 준으로 끝나는 이름을 가진 영화인을 조회하는 sql문
select * from actor
where name like '%준' and name like
'배%' and

select *
from actor
where country = '한국'
and name like '%준'
and name like '배%';

