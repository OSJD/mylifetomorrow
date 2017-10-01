package com.hg.mylifetomorrow.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.mylifetomorrow.domain.Profile;
import com.hg.mylifetomorrow.domain.Question;
import com.hg.mylifetomorrow.domain.Relationship;
import com.hg.mylifetomorrow.domain.User;
import com.hg.mylifetomorrow.exception.HgException;
import com.hg.mylifetomorrow.jdbc.ProfileRowMapper;
import com.hg.mylifetomorrow.jdbc.UserRowMapper;
import com.hg.mylifetomorrow.util.MLTUtil;

@Service
public class UserService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<Profile> getProfilesPendingVerification() throws HgException {

		 
		final List<Profile> profiles = new ArrayList<Profile>();
		try{
			final String sql = "SELECT PRO_ID,PRO_FNAME,PRO_LNAME,PRO_SUB_REL FROM HG_PROFILE WHERE PRO_VERFY='N'";
			final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			
			for (Map<String, Object> row : rows) {
				Profile profile = new Profile();
				profile.setDepFName((String)row.get("PRO_FNAME"));
				profile.setDepLName((String)row.get("PRO_LNAME"));
				profile.setDepId((int)row.get("PRO_ID"));
				profile.setRelationShipCode((String)row.get("PRO_SUB_REL"));
				profiles.add(profile);
			}
		}catch(Exception e){
			System.out.println("Error in getProfilesPendingVerification :::"+e);
			throw new HgException();
		}
			
		return profiles;
	
	}
	

	@Transactional
    public int createUser(User user) throws HgException {
		try{
	        final String currentDateTime=MLTUtil.getCurrentDateTime(MLTUtil.DT_FORMAT);
	        final StringBuilder sqlUser = new StringBuilder("INSERT INTO HG_USER (username, password, enabled, firstname,lastname,createdate,lastaccess)");
	        					sqlUser.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
	        final StringBuilder sqlUserRole = new StringBuilder("INSERT INTO HG_USER_ROLES (username, role)");
	        					sqlUserRole.append(" VALUES (?, ?)");   
	        final StringBuilder sqlUserProfile = new StringBuilder("INSERT INTO HG_PROFILE (username, PRO_FNAME, PRO_SUB_REL,PRO_LNAME) ");
	        sqlUserProfile.append(" VALUES(?,?,?,?)");
       					
	        int userStatus=jdbcTemplate.update(sqlUser.toString(), user.getUsername(), 
	        		   user.getPassword(),1, 
	        		   user.getFirstName(),
	        		   user.getLastName(),
	        		   currentDateTime,
	        		   currentDateTime);
	        if(userStatus==1){
	        	userStatus= jdbcTemplate.update(sqlUserRole.toString(),
	         		   user.getUsername(),user.getRole().name());
	        	if(userStatus==1){
	        		return jdbcTemplate.update(sqlUserProfile.toString(),
	 	         		   user.getUsername(),user.getFirstName(),Relationship.Subscriber.name(),user.getLastName());
	        	} else {
	        		return userStatus;
	        	}
	        } else {
	        	return userStatus;
	        }
        }catch(Exception e){
        	throw new HgException(e);
        }
    }
	
	public User getUser(final String userName){
		final String sql = "SELECT * FROM HG_USER WHERE username=?";
		 
		final User user = (User)jdbcTemplate.queryForObject(
				sql, new Object[] { userName }, new UserRowMapper());
			
		return user;
	}
	
	public List<Profile> getProfiles(final String userName){
		final String sql = "SELECT PRO_ID,PRO_FNAME,PRO_LNAME,PRO_SUB_REL FROM HG_PROFILE WHERE username=?";
		 
		final List<Profile> profiles = new ArrayList<Profile>();
		final Profile addprofile = new Profile();
		addprofile.setDepFName("Add Profile");
		addprofile.setDepId(0);
		profiles.add(addprofile);
		final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[] { userName });
		
		for (Map<String, Object> row : rows) {
			Profile profile = new Profile();
			profile.setDepFName((String)row.get("PRO_FNAME"));
			profile.setDepId((int)row.get("PRO_ID"));
			profile.setRelationShipCode((String)row.get("PRO_SUB_REL"));
			profiles.add(profile);
		}
			
		return profiles;
	}
	
	public int updateProfiles(final Profile profile)  throws HgException {
		try{
			final String sql ="UPDATE HG_PROFILE SET PRO_FNAME=?, PRO_LNAME=?, PRO_DOB=?, PRO_SUB_REL=?, PRO_POB=?, PRO_LAT_NE=?, PRO_LOG_NE=?,PRO_LAT_SW=?, PRO_LOG_SW=?,PRO_TOB=?,PRO_SEX=? WHERE PRO_ID=?";
	        final Object[] params = { profile.getDepFName(), profile.getDepLName(),profile.getDepDOB(),profile.getRelationShipCode()
	        		,profile.getDepPlaceOfBirth(),profile.getDepPlaceLatitudeNE(),profile.getDepPlaceLongitudeNE(),profile.getDepPlaceLatitudeSW(),profile.getDepPlaceLongitudeSW(),profile.getDepTime(),
	        		profile.getDepSex(),profile.getDepId()};
			
		    final int rows = jdbcTemplate.update(sql, params);
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }	

	}
	
	public int closeConversation(final Question question)  throws HgException {
		try{
			final String sql ="UPDATE HG_PRO_QUES SET RPLYD=? WHERE QUES_ID=?";
	        final Object[] params = { question.isReplied()==true?"Y":"N",question.getQuestionId()};
			
		    final int rows = jdbcTemplate.update(sql, params);
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }	

	}
	
	public int createQuestion(final Question question) throws HgException {
		try{
			final StringBuilder sql = new StringBuilder("INSERT INTO HG_PRO_QUES (PRO_ID,QUES_DESC, CREATE_TS,QUES_PSTD_BY) ");
								sql.append(" VALUES (?, ?, ?,?)");

						 
			final int rows=	jdbcTemplate.update(sql.toString(), new Object[] { question.getProfileId(),question.getQuestion(), question.getDatePosted(),question.getPostedBy()});
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }
	}
	
	public int createProfiles(final Profile profile)  throws HgException {
		try{
			final StringBuilder sql = new StringBuilder("INSERT INTO HG_PROFILE (username,PRO_FNAME, PRO_LNAME, PRO_DOB, PRO_SUB_REL, PRO_POB, PRO_LAT_NE, PRO_LOG_NE,PRO_LAT_SW, PRO_LOG_SW,PRO_TOB,PRO_SEX,PRO_CRT_TS) ");
								sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
						 
			final int rows=	jdbcTemplate.update(sql.toString(), new Object[] { profile.getUserName(),profile.getDepFName(), profile.getDepLName(),profile.getDepDOB(),profile.getRelationShipCode()
	        		,profile.getDepPlaceOfBirth(),profile.getDepPlaceLatitudeNE(),profile.getDepPlaceLongitudeNE(),profile.getDepPlaceLatitudeSW(),profile.getDepPlaceLongitudeSW(),profile.getDepTime(),
	        		profile.getDepSex(),profile.getCreateTimestamp()});
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }	

	}
	
	

	public int userChatConversation(final Question question)  throws HgException {
		try{
			final StringBuilder sql = new StringBuilder("INSERT INTO HG_QUES_CONV (QUES_ID,CONV_QUES,CONV_QUES_PSTD,QUES_PSTD_BY) ");
								sql.append(" VALUES (?, ?, ?,?)");
						 
			final int rows=	jdbcTemplate.update(sql.toString(), new Object[] { question.getQuestionId(),question.getQuestion(),MLTUtil.getCurrentDateTime(MLTUtil.DT_FORMAT),question.getPostedBy()});
			if(rows>0){
				setProfileShortName(question, question.getPostedBy());
			}
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }	

	}
	
	
	public int updateProfileAnalysis(final Profile profile)throws HgException {

		try{
			
			final String sql ="UPDATE HG_PROFILE SET PRO_ANALYSIS=?, PRO_VERFY=?, PRO_VERFY_TS=? WHERE PRO_ID=?";
	        final Object[] params = { profile.getAnalysis(), profile.isVerified()?"Y":"N",MLTUtil.getCurrentDateTime(MLTUtil.DT_FORMAT),profile.getDepId()};
			
		    final int rows = jdbcTemplate.update(sql, params);
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }	

	}
	
	public int uploadBirthChart(final Profile profile) throws HgException {
		try{
			final String sql ="UPDATE HG_PROFILE SET PRO_CHRT=?,PRO_CHRT_NM=? WHERE PRO_ID=?";
	        final Object[] params = { profile.getChartImgData(), profile.getChartImgName(),profile.getDepId()};
			
		    final int rows = jdbcTemplate.update(sql, params);
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }	

	}
	
	public User getPassword(final String userName){
		
		final String sql = "SELECT  firstname,lastname,password FROM HG_USER WHERE username=?";
		
		User user=null;
		try{
			user = (User)jdbcTemplate.queryForObject(
				sql, new Object[] { userName }, new RowMapper<User>(){

					@Override
					public User mapRow(ResultSet rs, int arg1) throws SQLException {	
						final User user=new User();
						user.setPassword(rs.getString("password"));
						user.setFirstName(rs.getString("firstname"));
						user.setLastName(rs.getString("lastname"));
						return user;
					}
					
				});
			return user;
		}catch(EmptyResultDataAccessException  e){
			System.out.println(e);
			return null;
		}
		
		
	}
	
	public List<Question> getAllPendingQuestions() throws ParseException{

		
		final String sql = "SELECT PRO_ID,QUES_ID,QUES_DESC,CREATE_TS,QUES_PSTD_BY FROM HG_PRO_QUES WHERE RPLYD='N'";
		
		final List<Question> questions=new ArrayList<Question>();
		
		final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		for (Map<String, Object> row : rows) {
			final Question question=new Question();
			question.setQuestionId((int)row.get("QUES_ID"));
			question.setProfileId((int)row.get("PRO_ID"));
			question.setQuestion((String)row.get("QUES_DESC"));
			question.setDatePosted(MLTUtil.getDateInDisplayFormat((String)row.get("CREATE_TS")));
			question.setPostedBy((String)row.get("QUES_PSTD_BY"));
			questions.add(question);
		}
		 
		
		return questions;
	}
	
	public List<Question> getChatComments(final int questionId) throws ParseException{


		final String sql = "SELECT CONV_QUES,CONV_QUES_PSTD,QUES_PSTD_BY FROM HG_QUES_CONV WHERE QUES_ID=?";
		
		final List<Question> questions=new ArrayList<Question>();
		
		final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[]{questionId});
		
		for (Map<String, Object> row : rows) {
			final Question question=new Question();
			question.setQuestionId(questionId);
			question.setQuestion((String)row.get("CONV_QUES"));
			question.setDatePosted((String)row.get("CONV_QUES_PSTD"));
			final String user=(String)row.get("QUES_PSTD_BY");
			setProfileShortName(question,user);
			questions.add(question);
		}
		 
		
		return questions;
	}
	
	private void setProfileShortName(final Question question,final String user){
		if(user!=null && !user.isEmpty()){
			question.setPostedBy(user);
			if(user.equalsIgnoreCase("Guru Admin")){
				question.setShortName("GA");
				question.setColor("e7557a");
			} else {
				String lName=user.split(" ")[0];
				String fName=user.split(" ")[1];				
				question.setShortName(lName.substring(0,1)+""+fName.substring(0,1));
				question.setColor("55C1E7");
			}
		}
	}
	
	public Question getQuestionById(final int questionId) throws HgException{

		try{
		
			final String sql = "SELECT QUES_ID,QUES_DESC,CREATE_TS,QUES_PSTD_BY,RPLYD FROM HG_PRO_QUES WHERE QUES_ID=?";
		
			final Question question = (Question)jdbcTemplate.queryForObject(
				sql, new Object[] { questionId }, new RowMapper<Question>(){

					@Override
					public Question mapRow(ResultSet rs, int arg1) throws SQLException {
						final Question question=new Question();
						question.setQuestionId(rs.getInt("QUES_ID"));
						question.setQuestion(rs.getString("QUES_DESC"));
						question.setDatePosted(rs.getString("CREATE_TS"));
						final String replied=rs.getString("RPLYD");
						question.setReplied(replied.equalsIgnoreCase("Y")?true:false);
						final String user=rs.getString("QUES_PSTD_BY");
						setProfileShortName(question,user);
						
						return question;
					}
					
				});
			return question;
		}catch(Exception e){
			throw new HgException(e);
		}
 
		
		
	}
	
	public List<Question> getProfileQuestions(final int profileId) throws ParseException{

		
		final String sql = "SELECT QUES_ID,QUES_DESC,CREATE_TS,QUES_PSTD_BY,RPLYD FROM HG_PRO_QUES WHERE PRO_ID=?";
		
		final List<Question> questions=new ArrayList<Question>();
		
		final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[]{profileId});
		
		for (Map<String, Object> row : rows) {
			final Question question=new Question();
			question.setProfileId(profileId);
			question.setQuestionId((int)row.get("QUES_ID"));
			question.setQuestion((String)row.get("QUES_DESC"));
			question.setDatePosted((String)row.get("CREATE_TS"));
			final String replied=(String)row.get("RPLYD");
			question.setReplied(replied.equalsIgnoreCase("Y")?true:false);
			final String user=(String)row.get("QUES_PSTD_BY");
			setProfileShortName(question,user);
			question.setNoQuestions(true);
			questions.add(question);
		}
		 
		
		return questions;
	}
	
	public Question getQuestionIdByTime(final int profileId,final String timeStamp){
		final String sql = "SELECT QUES_ID,QUES_PSTD_BY FROM HG_PRO_QUES WHERE PRO_ID=? and CREATE_TS=?";
		 
		final Question question = (Question)jdbcTemplate.queryForObject(
				sql, new Object[] { profileId,timeStamp }, new RowMapper<Question>(){

					@Override
					public Question mapRow(ResultSet rs, int arg1) throws SQLException {
						final Question question=new Question();
						question.setQuestionId(rs.getInt("QUES_ID"));
						final String user=rs.getString("QUES_PSTD_BY");
						setProfileShortName(question,user);
						return question;
					}
					
				});
			
		return question;
	}
	
	public List<Profile> searchProfile(final String searchCriteria){
		
	
		final String sql = "SELECT username,PRO_FNAME,PRO_LNAME,PRO_ID FROM HG_PROFILE WHERE PRO_ID LIKE :PID OR PRO_LNAME LIKE :LNAME OR PRO_FNAME LIKE :FNAME OR username LIKE :UNAME";
		final Map<String,Object> params = new HashMap<String,Object>();
			    params.put("PID", "%"+searchCriteria+"%");
			    params.put("LNAME", "%"+searchCriteria+"%");
			    params.put("FNAME", "%"+searchCriteria+"%");
			    params.put("UNAME", "%"+searchCriteria+"%");
		final List<Map<String, Object>> rows=new NamedParameterJdbcTemplate(jdbcTemplate).queryForList(sql, params);
		final List<Profile> profiles=new ArrayList<Profile>();
		for (Map<String, Object> row : rows) {
			final Profile profile=new Profile();
			profile.setDepId((int)row.get("PRO_ID"));
			profile.setDepLName((String)row.get("PRO_LNAME"));
			profile.setDepFName((String)row.get("PRO_FNAME"));
			profile.setUserName((String)row.get("username"));

			profiles.add(profile);
		}
		
		return profiles;
		
	}
	
	public Profile getProfileById(final int profileId){
		final String sql = "SELECT username,PRO_FNAME,PRO_LNAME,PRO_DOB,PRO_SUB_REL,PRO_POB,PRO_LAT_NE,PRO_LOG_NE,PRO_LAT_SW,PRO_LOG_SW,PRO_TOB,PRO_SEX,PRO_CHRT,PRO_CHRT_NM,PRO_VERFY,PRO_ANALYSIS FROM HG_PROFILE WHERE PRO_ID=?";
		 
		final Profile profile = (Profile)jdbcTemplate.queryForObject(
				sql, new Object[] { profileId }, new ProfileRowMapper());
		profile.setDepId(profileId);
		return profile;
	}
	
	public int deleteQuestion(final int questionId) throws HgException {
		try{
			final StringBuilder sql = new StringBuilder("DELETE FROM HG_PRO_QUES  WHERE QUES_ID = ?");
						 
			final int rows=	jdbcTemplate.update(sql.toString(), new Object[] { questionId});
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }
	}
	
	public int deleteQuestionChat(final int questionId) throws HgException {
		try{
			final StringBuilder sql = new StringBuilder("DELETE FROM HG_QUES_CONV  WHERE QUES_ID = ?");
						 
			final int rows=	jdbcTemplate.update(sql.toString(), new Object[] { questionId});
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }
	}
	
	public int deleteProfile(final int depId) throws HgException {
		try{
			final StringBuilder sql = new StringBuilder("DELETE FROM HG_PROFILE WHERE PRO_ID = ?");
						 
			final int rows=	jdbcTemplate.update(sql.toString(), new Object[] { depId});
			
		    return rows;
		}catch(Exception e){
			System.out.println("ERROR ::::"+e);
        	throw new HgException(e);
        }
	}
	
	public Profile getProfileByTS(final long ts){
		final String sql = "SELECT PRO_ID,PRO_FNAME,PRO_LNAME,PRO_SUB_REL FROM HG_PROFILE WHERE PRO_CRT_TS=?";
		 
		Profile profile=jdbcTemplate.queryForObject(sql,new Object[] { ts },new RowMapper<Profile>(){

			@Override
			public Profile mapRow(ResultSet rs, int arg1) throws SQLException {
				final Profile profile=new Profile();
				profile.setDepId(rs.getInt("PRO_ID"));
				profile.setDepFName(rs.getString("PRO_FNAME"));
				profile.setDepLName(rs.getString("PRO_LNAME"));
				profile.setRelationShipCode(rs.getString("PRO_SUB_REL"));
				return profile;
			}
			
		});
			
		return profile;
	}
}
