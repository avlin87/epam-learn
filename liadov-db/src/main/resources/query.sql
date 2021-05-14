select *
from products p
where upper(p.title) like upper('academy q%')
;

select *
from products p
where p.price = 9.99
  and p.category = 8
order by p.category, p.price
;

select *
from products p
where p.category in (8, 15)
;

select *
from products p
where p.price between 10 and 20
;

select *
from orders o
where o.orderdate between date ('2004-01-05') and date ('2004-02-05')
;

select o.customerid, count(*)
from orders o
group by o.customerid
;

select o.customerid, o.orderdate, sum(o.totalamount) as sumtotalamount
from orders o
group by o.customerid, o.orderdate
having sum(o.totalamount) > 100
;

select cust.firstname, cust.lastname, prod.title, o.orderdate
from customers cust
         join orders o on cust.customerid = o.customerid
         join orderlines ordLine on o.orderid = ordline.orderid
         join products prod on ordLine.prod_id = prod.prod_id
;