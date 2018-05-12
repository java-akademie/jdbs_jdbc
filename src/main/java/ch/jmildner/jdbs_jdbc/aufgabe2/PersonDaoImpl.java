package ch.jmildner.jdbs_jdbc.aufgabe2;

import ch.jmildner.tools.MyPoolingDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class PersonDaoImpl implements PersonDao
{

    private final DataSource DS;

    public PersonDaoImpl(final String DATABASE) 
    {
        this.DS = new MyPoolingDataSource(DATABASE).getDataSource();
    }

    @Override
    public void create() throws SQLException
    {
        try (Connection c = DS.getConnection();
                Statement s = c.createStatement())
        {
            s.execute(CREATE);
        }
    }

    @Override
    public void drop() throws SQLException
    {
        try (Connection c = DS.getConnection();
                Statement s = c.createStatement())
        {
            try
            {
                s.execute(DROP);
            }
            catch (SQLException e)
            {
            }
        }
    }

    @Override
    public int delete(Person p) throws SQLException
    {
        return deleteById(p.getId());
    }

    @Override
    public int deleteById(Integer id) throws SQLException
    {
        try (Connection c = DS.getConnection();
                PreparedStatement ps = c.prepareStatement(DELETE_BY_ID))
        {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    @Override
    public int deleteByName(String name) throws SQLException
    {
        try (Connection c = DS.getConnection();
                PreparedStatement ps = c.prepareStatement(DELETE_BY_NAME))
        {
            ps.setString(1, name);
            return ps.executeUpdate();
        }
    }

    @Override
    public int insert(Person p) throws SQLException
    {
        try (Connection c = DS.getConnection();
                PreparedStatement ps = c.prepareStatement(INSERT))
        {
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getAddr());
            int inserted = ps.executeUpdate();
            return inserted;
        }
    }

    @Override
    public int update(Person p) throws SQLException
    {
        try (Connection c = DS.getConnection();
                PreparedStatement ps = c.prepareStatement(UPDATE))
        {
            ps.setInt(3, p.getId());
            ps.setString(1, p.getName());
            ps.setString(2, p.getAddr());
            int updated = ps.executeUpdate();
            return updated;
        }
    }

    @Override
    public List<Person> getAll() throws SQLException
    {
        try (Connection c = DS.getConnection();
                Statement s = c.createStatement())
        {
            List<Person> personen = new ArrayList<>();
            ResultSet rs = s.executeQuery(GET_ALL);
            while (rs.next())
            {
                personen.add(new Person(rs.getInt(1), rs.getString(2),
                        rs.getString(3)));
            }
            return personen;
        }
    }

    @Override
    public Person getById(Integer id) throws SQLException
    {
        try (Connection c = DS.getConnection();
                PreparedStatement ps = c.prepareStatement(GET_BY_ID))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                return new Person(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            return null;
        }
    }

    @Override
    public List<Person> getByName(String name) throws SQLException
    {
        try (Connection c = DS.getConnection();
                PreparedStatement ps = c.prepareStatement(GET_BY_NAME))
        {
            List<Person> personen = new ArrayList<>();
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                personen.add(new Person(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            return personen;
        }
    }
}
