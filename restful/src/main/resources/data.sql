insert into User(id, birth_date, name)  values(100001,  sysdate(), 'Arindam')
insert into User(id, birth_date, name)  values(100002,  sysdate(), 'Jayita')
insert into User(id, birth_date, name)  values(100003,  sysdate(), 'Migu')
insert into post (id, description , user_id ) values (10001, 'Posts by Arindam', 100001)
insert into post (id, description , user_id ) values (10002, 'Posts by Arindam2', 100001)
insert into post (id, description , user_id ) values (10003, 'Posts by Arindam3', 100001)
insert into post (id, description , user_id ) values (10004, 'Posts by Jayita', 100002)
insert into post (id, description , user_id ) values (10005, 'Posts by Migu', 100003)
insert into post (id, description , user_id ) values (10006, 'Posts by Migu2', 100003)