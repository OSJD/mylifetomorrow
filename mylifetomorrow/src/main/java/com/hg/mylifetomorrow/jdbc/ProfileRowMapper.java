package com.hg.mylifetomorrow.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hg.mylifetomorrow.domain.Profile;

public class ProfileRowMapper implements RowMapper<Profile> {

	public Profile mapRow(ResultSet rs, int arg1) throws SQLException {
		final Profile profile=new Profile();
		profile.setUserName(rs.getString("username"));
		profile.setDepFName(rs.getString("PRO_FNAME"));
		profile.setDepLName(rs.getString("PRO_LNAME"));
		profile.setDepDOB(rs.getString("PRO_DOB"));
		profile.setRelationShipCode(rs.getString("PRO_SUB_REL"));
		profile.setDepPlaceOfBirth(rs.getString("PRO_POB"));
		profile.setDepPlaceLatitudeNE(rs.getString("PRO_LAT_NE"));
		profile.setDepPlaceLongitudeNE(rs.getString("PRO_LOG_NE"));
		profile.setDepPlaceLatitudeSW(rs.getString("PRO_LAT_SW"));
		profile.setDepPlaceLongitudeSW(rs.getString("PRO_LOG_SW"));
		profile.setDepTime(rs.getString("PRO_TOB"));
		profile.setDepSex(rs.getString("PRO_SEX"));
		profile.setChartImgData(rs.getString("PRO_CHRT"));
		profile.setChartImgName(rs.getString("PRO_CHRT_NM"));
		String verify=rs.getString("PRO_VERFY");
		profile.setVerified(verify.equalsIgnoreCase("Y")?true:false);
		profile.setAnalysis(rs.getString("PRO_ANALYSIS"));
		return profile;
	}

}
