package com.hg.mylifetomorrow.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.URIUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hg.mylifetomorrow.domain.EmailData;
import com.hg.mylifetomorrow.domain.Message;
import com.hg.mylifetomorrow.domain.Profile;
import com.hg.mylifetomorrow.domain.Question;
import com.hg.mylifetomorrow.domain.User;
import com.hg.mylifetomorrow.exception.HgException;
import com.hg.mylifetomorrow.service.EmailService;
import com.hg.mylifetomorrow.service.UserService;
import com.hg.mylifetomorrow.util.MLTUtil;

@Controller
public class MLTUserController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private EmailService emailService;
	
	@RequestMapping(value = {"/dependents" })
	public @ResponseBody Message getDependents(Principal user){
		
		final Message depList=new Message();
		List<Profile> profiles=userService.getProfiles(user.getName());
		depList.setProfiles(profiles);		
		return depList;
		
	}
	
	@RequestMapping(value = {"/loggedInUser" })
	public @ResponseBody User getLoggedInUser(Principal user){
		final User userObj=userService.getUser(user.getName());
		return userObj;
	}
	
	private Map<Integer,List<Question>> renderQuestions(final List<Question> questions){
		final Map<Integer,List<Question>> questionMap=new HashMap<Integer,List<Question>>();

		int mcount=1;
		for(Question question :questions){
			if(questionMap.containsKey(mcount)){
				questionMap.get(mcount).add(question);
			} else {
				questionMap.put(mcount,new ArrayList<Question>());
				questionMap.get(mcount).add(question);
			}
			if(questionMap.get(mcount).size()==2){
				mcount=mcount+1;
			}

		}
		
		return questionMap;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/profile/verifyaddress", method = RequestMethod.POST)
	public @ResponseBody LinkedHashMap<String,Object> verifyAddress(@RequestBody String address){

		try {

	        URL url = new URL(
	                "http://maps.googleapis.com/maps/api/geocode/json?address="
	                        + URIUtil.encodeQuery(address) + "&sensor=true");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");

	        if (conn.getResponseCode() != 200) {
	            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	        }
	        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

	        String output = "", full = "";
	        while ((output = br.readLine()) != null) {
	            full += output;
	        }
	        	        
	        conn.disconnect();
	        
	        ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(full, Map.class);
	        ArrayList<LinkedHashMap<String,Object>> addressAttr=(ArrayList<LinkedHashMap<String,Object>>)map.get("results");
	        if(addressAttr!=null && addressAttr.size()>0){
		        final LinkedHashMap<String,Object> addAttrMap=addressAttr.get(0);
		        final LinkedHashMap<String,Object> geoMap=(LinkedHashMap<String,Object>)addAttrMap.get("geometry");
		        final LinkedHashMap<String,Object> geoViewPointMap=(LinkedHashMap<String,Object>)geoMap.get("viewport");
		        return geoViewPointMap;
	        }
	        
	        return new LinkedHashMap<String,Object>();
	    } catch (MalformedURLException e) {
	       System.out.println(e);
	    } catch (IOException e) {
	    	 System.out.println(e);
	    }
		
        return new LinkedHashMap<String,Object>();
	
	}
	
	@Async
	public void sendEmailWithPassword(final EmailData emailData) {

		try {
			emailService.createEmail(emailData);
		} catch (AddressException e) {
			System.out.println(e);
		} catch (MessagingException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public @ResponseBody Message forgotPassword(@RequestBody User userIn) {
		final Message message=new Message();
		final User user=userService.getPassword(userIn.getUsername());
		if(user!=null){
			final EmailData emailData=new EmailData();
			emailData.setBodyTemplate("SIMPLE");
			emailData.setTo(userIn.getUsername());
			emailData.setBodyQ(user.getPassword());
			emailData.setUserName(user.getLastName()+" "+user.getFirstName());
			sendEmailWithPassword(emailData);
			message.setSuccess(true);
			message.setMessage("Email send to registered email id.");
			return message;
		}else{
			message.setSuccess(false);
			message.setMessage("ERROR");
			return message;
		}
	}
	
	@RequestMapping(value = "/profile/questions", method = RequestMethod.GET)
    public @ResponseBody Message getAllProfileQuestions() {

		final Message msg=new Message();
		try {
			List<Question> questions = userService.getAllPendingQuestions();
		
				if(questions.size()>0){
					msg.setSuccess(true);
					msg.setPendingQuestions(questions);			
				} else {
					final List<Question> noQuestion=new ArrayList<Question>();
					final Question question=new Question();
					question.setQuestion("No questions posted!");
					question.setNoQuestions(false);
					question.setQuestionId(0);
					noQuestion.add(question);
					msg.setSuccess(false);
					msg.setQuestions(renderQuestions(noQuestion));	
				}
		} catch (ParseException e) {
				System.out.println("ERROR ::"+e);
				msg.setSuccess(false);
				msg.setMessage("Failed to pull questions!");	
		}

        return msg;

    }
	
	@RequestMapping(value = "/profile/viewcomments/{questionId}", method = RequestMethod.GET)
    public @ResponseBody Message getChatComments(@PathVariable("questionId") int questionId) {

		final Message msg=new Message();
		try {
			List<Question> questions1=new ArrayList<Question>();
			questions1.add(userService.getQuestionById(questionId));
			List<Question> questions = userService.getChatComments(questionId);
			if(questions!=null && questions.size()>0){
				questions1.addAll(questions);
			}

			if(questions1.size()>0){
				msg.setSuccess(true);
				msg.setChatConversation(questions1);	
			} 
		} catch (ParseException e) {
				System.out.println("ERROR ::"+e);
				msg.setSuccess(false);
				msg.setMessage("Failed to pull questions!");	
		} catch (HgException e) {
			System.out.println("ERROR ::"+e);
			msg.setSuccess(false);
			msg.setMessage("Failed to pull questions!");	
		}

        return msg;

    }
	
	@RequestMapping(value = "/profile/question/{depId}", method = RequestMethod.GET)
    public @ResponseBody Message getProfileQuestions(@PathVariable("depId") int depId) {

		final Message msg=new Message();
		try {
			List<Question> questions = userService.getProfileQuestions(depId);
		
				if(questions.size()>0){
					msg.setSuccess(true);
					msg.setQuestions(renderQuestions(questions));	
					
				} else {
					final List<Question> noQuestion=new ArrayList<Question>();
					final Question question=new Question();
					question.setQuestion("No questions posted!");
					question.setNoQuestions(false);
					question.setQuestionId(0);
					noQuestion.add(question);
					msg.setSuccess(false);
					msg.setQuestions(renderQuestions(noQuestion));	
				}
		} catch (ParseException e) {
				System.out.println("ERROR ::"+e);
				msg.setSuccess(false);
				msg.setMessage("Failed to pull questions!");	
		}

        return msg;

    }
	
	@RequestMapping(value = "/profile/search/{searchCriteria}", method = RequestMethod.GET)
    public @ResponseBody List<Profile> searchProfile(@PathVariable("searchCriteria") String searchCriteria) {

		final List<Profile> profiles=userService.searchProfile(searchCriteria);
        return profiles;

    }
	
	@RequestMapping(value = "/profile/{depId}", method = RequestMethod.GET)
    public @ResponseBody Profile getProfileById(@PathVariable("depId") int depId) {

		final Profile profile=userService.getProfileById(depId);
        return profile;

    }
	
	
	@RequestMapping(value = "/admin/verification", method = RequestMethod.POST)
    public @ResponseBody Message updateProfileAnalysis(@RequestBody Profile profile) {
		final Message msg=new Message();
		try {
			final int status=userService.updateProfileAnalysis(profile);
			if(status==0){
				msg.setSuccess(false);
				msg.setMessage("Failed to save profile analysis!");
			}
			msg.setSuccess(true);
		} catch (HgException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}

		return msg;
	}
	
	@RequestMapping(value="/admin/fileUpload/{profileId}", method = RequestMethod.POST)
    public @ResponseBody Message uploadFile(@PathVariable("profileId") int profileId,@RequestPart("file") MultipartFile file)  {
		
		final Message msg=new Message();
		try {
			 if(file.getContentType().equalsIgnoreCase("image/jpeg")){
				 final String fileName=file.getOriginalFilename();
				 final byte[] fileBytes=file.getBytes();
				 final byte[] encoded=Base64.encodeBase64(fileBytes);
				 final String encodedString = new String(encoded);
				 final Profile profile=new Profile();
				 profile.setDepId(profileId);
				 profile.setChartImgData(encodedString);
				 profile.setChartImgName(fileName);
				 userService.uploadBirthChart(profile);
				 msg.setSuccess(true);
				 msg.setMessage(encodedString);
			 }else {
				 msg.setSuccess(false);
				 msg.setMessage("Please upload image file only!");
			 }

		} catch (IOException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		} catch (HgException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}
        return msg;
    }
	
	@RequestMapping(value = "/admin/verify", method = RequestMethod.GET)
	public @ResponseBody Message getProfilesPendingVerification(Principal user){
		
		final Message profList=new Message();
		try{
			List<Profile> profiles=userService.getProfilesPendingVerification();
			profList.setSuccess(true);
			profList.setProfiles(profiles);		
		}catch(Exception e){
			profList.setSuccess(false);
			profList.setMessage(e.getMessage());
		}
		
		return profList;
	}
	
	
	@RequestMapping(value = "/profile/deletequestion/{questionId}", method = RequestMethod.GET)
	@Transactional
    public @ResponseBody Message deleteQuestionById(@PathVariable("questionId") int questionId) {
		
		
		System.out.println("-----------------------> QUESTION ID::"+questionId);
		final Message msg=new Message();
		try {
			int deleteStatus=userService.deleteQuestionChat(questionId);
			deleteStatus=userService.deleteQuestion(questionId);
			if(deleteStatus>0){
				msg.setSuccess(true);
				msg.setMessage("Question deleted successfully!");
			}else{
				msg.setSuccess(false);
				msg.setMessage("Question failed to delete.");
			}

		} catch (HgException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}
        return msg;

    }
	
	@RequestMapping(value = "/profile/delete/{depId}", method = RequestMethod.GET)
    public @ResponseBody Message deleteProfileById(@PathVariable("depId") int depId) {
		final Message msg=new Message();
		try {
			final int deleteStatus=userService.deleteProfile(depId);
			if(deleteStatus>0)
				msg.setSuccess(true);
			else {
				msg.setSuccess(false);
				msg.setMessage("Profile failed to delete.");
			}
		} catch (HgException e) {
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}
        return msg;

    }
	
	@RequestMapping(value = "/profile/postquestion", method = RequestMethod.POST)
    public @ResponseBody Message createQuestion(@RequestBody Question question) {
		
		System.out.println("---------------------------> REPLY:::::"+question.isReplied());
		int status=0;
		final Message msg=new Message();
		try {
			String currentDate=MLTUtil.getCurrentDateTime(MLTUtil.DT_FORMAT);
			question.setDatePosted(currentDate);
			if(question.getQuestionId()==0){
				status = userService.createQuestion(question);
		        if(status>0){
		        	final Question qId=userService.getQuestionIdByTime(question.getProfileId(),currentDate);
		        	if(qId!=null){
		        		question.setQuestionId(qId.getQuestionId());
		        		question.setPostedBy(qId.getPostedBy());
		        		question.setShortName(qId.getShortName());
		        		question.setColor(qId.getColor());
		        	}		        	
		        	msg.setSuccess(true);
		        	msg.setCurrentQuestion(question);
		        	if(question.getPostedBy().equalsIgnoreCase("Guru Admin")){
		        		final Profile profile=userService.getProfileById(question.getProfileId());
		        		try {
		        			final EmailData emailData=new EmailData();
		        			emailData.setTo(profile.getUserName());
		        			emailData.setUserName(profile.getDepFName()+" "+profile.getDepLName());
		        			emailData.setBodyQ(question.getQuestion());
		        			emailData.setQuesOrReply("Q");
		        			emailData.setBodyTemplate("COMPLEX");
							emailService.createEmail(emailData);
						} catch (Exception e) {
							System.out.println("EMAIL FAILED."+e);
						}
		        		
		        	}
		        } else {
		        	msg.setSuccess(false);
		        	msg.setMessage("Failed to post question!");
		        }
			} else {
				status=userService.userChatConversation(question);
				if(status>0){
					final Question actualQ=userService.getQuestionById(question.getQuestionId());
					final Profile profile=userService.getProfileById(question.getProfileId());
		        	msg.setSuccess(true);
		        	msg.setCurrentQuestion(question);
		        	if(question.getPostedBy().equalsIgnoreCase("Guru Admin")){
		        		try {
		        			final EmailData emailData=new EmailData();
		        			emailData.setTo(profile.getUserName());
		        			emailData.setUserName(profile.getDepFName()+" "+profile.getDepLName());
		        			emailData.setBodyQ(actualQ.getQuestion());
		        			emailData.setBodyR(question.getQuestion());
		        			emailData.setQuesOrReply("R");
		        			emailData.setBodyTemplate("COMPLEX");
							emailService.createEmail(emailData);
						} catch (Exception e) {
							System.out.println("EMAIL FAILED."+e);
						}
		        		
		        	}		        	
				}else{
		        	msg.setSuccess(false);
		        	msg.setMessage("Failed to post question!");
				}
			}
        	if(question.isReplied()){
        		userService.closeConversation(question);
        	}


		} catch (HgException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage("Failed to post question!");
		}
        

        return msg;

    }
	
	@RequestMapping(value = "/profile/create", method = RequestMethod.POST)
    public @ResponseBody Message createProfile(@RequestBody Profile profile,Principal user) {

		int status;
		final Message msg=new Message();
		try {
			profile.setUserName(user.getName());
			final long ts=System.currentTimeMillis();
			profile.setCreateTimestamp(ts);
				System.out.println("---------------------------------------> ::::"+profile.getDepTime());

			status = userService.createProfiles(profile);
	        if(status>0){
	        	msg.setSuccess(true);
	        	final Profile latestProfile=userService.getProfileByTS(ts);
	        	msg.setCurrentProfile(latestProfile);	
	        } else {
	        	msg.setSuccess(false);
	        }
		} catch (HgException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}
        

        return msg;

    }
	
	
	@RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    public @ResponseBody Message updateProfile(@RequestBody Profile profile) {

		int status;
		final Message msg=new Message();
		try {

				System.out.println("---------------------------------------> ::::"+profile.getDepDOB());

			status = userService.updateProfiles(profile);
	        if(status>0){
	        	msg.setSuccess(true);
	        } else {
	        	msg.setSuccess(false);
	        	msg.setMessage("Profile( "+profile.getDepFName()+" ) failed to update!");
	        }
		} catch (HgException e) {
			System.out.println(e);
			msg.setSuccess(false);
			msg.setMessage(e.getMessage());
		}
        

        return msg;

    }
	


}
