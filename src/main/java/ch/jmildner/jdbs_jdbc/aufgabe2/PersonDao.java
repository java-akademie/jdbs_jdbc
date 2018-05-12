package ch.jmildner.jdbs_jdbc.aufgabe2;

import java.sql.SQLException;
import java.util.List;

public interface PersonDao
{

    String GET_BY_ID = "select id, name, addr from person2 where id=?";
    String GET_BY_NAME = "select id, name, addr from person2 where name like ?";
    String GET_ALL = "select id, name, addr from person2";
    String DELETE_BY_ID = "delete from person2 where id=?";
    String DELETE_BY_NAME = "delete from person2 where name like ?";
    String UPDATE = "update person2 set name=?, addr=? where id =?";
    String INSERT = "insert into person2 values(?,?,?)";
    String DROP = "drop table person2";
    String CREATE = "create table person2 "
            + "(id int not null, name varchar(30), addr varchar(50), primary key(id))";

    void            drop        ()              throws SQLException;
    void            create      ()              throws SQLException;
    int             insert      (Person p)      throws SQLException;
    int             update      (Person p)      throws SQLException;
    int             delete      (Person p)      throws SQLException;
    int             deleteById  (Integer id)    throws SQLException;
    int             deleteByName(String name)   throws SQLException;
    List<Person>    getAll      ()              throws SQLException;
    List<Person>    getByName   (String name)   throws SQLException;
    Person          getById     (Integer id)    throws SQLException;
}
