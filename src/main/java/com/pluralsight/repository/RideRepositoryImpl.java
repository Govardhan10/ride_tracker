package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Ride> getRides() {
		return jdbcTemplate.query("select * from ride", new RideRowMapper());
	}

	// Demonstrating simpleJdbcInsert
	@Override
	public Ride createRide(Ride ride) {
		//jdbcTemplate.update("insert into ride (name, duration) values (?,?)",ride.getName(),ride.getDuration());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into ride (name, duration) values (?,?)", new String[]{"id"});
            ps.setString(1,ride.getName());
            ps.setInt(2,ride.getDuration());
            return ps;
        },keyHolder);
		Number id = keyHolder.getKey();

		return getRide(id.intValue());
	}

    @Override
    public Ride getRide(Integer id) {
	    return jdbcTemplate.queryForObject("select * from ride where id = ?",new RideRowMapper(),id);
    }

    @Override
    public void updateRide(Ride ride) {
        jdbcTemplate.update("update ride set name = ? , duration = ? where id = ?",
                ride.getName(), ride.getDuration(), ride.getId());
    }

    @Override
    public void batchUpdate(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?",pairs);
    }

    @Override
    public void deleteRide(Integer id) {
        //jdbcTemplate.update("delete from ride where id = ?", id); -- simple jdbc delete
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> namedParams = new HashMap<>();
        namedParams.put("id",id);

        namedTemplate.update("delete from ride where id = :id",namedParams);
    }
}
