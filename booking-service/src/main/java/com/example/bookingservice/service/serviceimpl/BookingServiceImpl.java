package com.example.bookingservice.service.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.bookingservice.dto.Play;
import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.service.BookingService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Value("${play.url}")
	private String playUrl;
	
	@Value("${kafka.url}")
	private String kafkaUrl;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Override
	@Transactional
	public Booking addBooking(Booking booking,HttpServletRequest request) throws BookingServiceDaoException {
		try {
			logger.info("Entered into booking services");
			String authorization = request.getHeader("Authorization");
           
            
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Authorization", authorization);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

 

            HttpEntity requestEntity = new HttpEntity(requestHeaders);
            
            APISuccessResponse response = restTemplate.exchange(
            		playUrl+booking.getPlayId()
            		,HttpMethod.GET
            		,requestEntity
            		,APISuccessResponse.class).getBody();
            
            Play play=objectMapper.convertValue(response.getBody(), new TypeReference<Play>() {});
            
            if(play.getSeatsAvailable()-booking.getSeatCount()>=0) {
            	
            	
            	
            	play.setSeatsAvailable(play.getSeatsAvailable()-booking.getSeatCount());
            	
            	HttpEntity<Play> updateRequest =  new HttpEntity<>(play,requestHeaders);
            	
            	APISuccessResponse apiResponse = restTemplate.exchange(
                		playUrl
                		,HttpMethod.PUT
                		,updateRequest
                		,APISuccessResponse.class).getBody();
            	
            		logger.info(apiResponse.toString());
            	
            	  restTemplate.exchange(kafkaUrl+apiResponse.getBody().toString(), HttpMethod.GET,null,String.class);
            	
            		Booking bookingResponse = bookingRepository.save(booking);
//            		
//            	  webClientBuilder.build().get().uri("http://kafka-service/kafka/publish/"+bookingResponse.toString()).retrieve().bodyToMono(String.class);
            	  
            	return bookingResponse;
            	
            	
            }
            else {
            	throw new BookingServiceDaoException(" Required Seats are  Unavailable seats are full");
            }
            
//            Play play = getPlayObjcetMapper(response.getBody().getBody().toString());
            
            //logger.info(play.toString());
			
          
			
            
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to Book", ex.getCause());
		}
		catch(Exception ex) {
			throw new BookingServiceDaoException(ex.getMessage(), ex.getCause());
		}

	}

	@Override
	@Transactional
	public List<Booking> getAllBooking() throws BookingServiceDaoException {
		try {
			List<Booking> bookings = bookingRepository.findAll();
			if (bookings.size() > 0) {
				return bookings;
			} else {
				throw new BookingServiceDaoException("No Bookings Data the list is empty");
			}
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to fetch all Booking ", ex.getCause());
		}
	}

	@Override
	@Transactional
	public Booking getBookingById(int bookingId, HttpServletRequest request) throws BookingServiceDaoException {
		try {
			Optional<Booking> booking = bookingRepository.findById(bookingId);
			if (booking.isPresent()) {
				return booking.get();
			} else {
				throw new BookingServiceDaoException("No Record Found for the id - " + bookingId);
			}
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to fetch Booking of id " + bookingId, ex.getCause());
		}
	}

	@Override
	@Transactional
	public Booking updateBooking(Booking booking,HttpServletRequest request) throws BookingServiceDaoException {
		try {
			logger.info("Entered into update booking services");
			String authorization = request.getHeader("Authorization");
           
            
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Authorization", authorization);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

 

            HttpEntity requestEntity = new HttpEntity(requestHeaders);
            
            APISuccessResponse response = restTemplate.exchange(
            		playUrl+booking.getPlayId()
            		,HttpMethod.GET
            		,requestEntity
            		,APISuccessResponse.class).getBody();
            
            Play play=objectMapper.convertValue(response.getBody(), new TypeReference<Play>() {});
            
            if(play.getSeatsAvailable()-booking.getSeatCount()>=0) {
            	
            	play.setSeatsAvailable(play.getSeatsAvailable()-booking.getSeatCount());
            	
     
            	HttpEntity<Play> updateRequest =  new HttpEntity<>(play,requestHeaders);
            	
            	APISuccessResponse apiResponse = restTemplate.exchange(
                		playUrl
                		,HttpMethod.PUT
                		,updateRequest
                		,APISuccessResponse.class).getBody();
            	
            	logger.info(apiResponse.toString());
            	
            	return bookingRepository.save(booking);
            	
            	
            }
            else {
            	throw new BookingServiceDaoException(" Required Seats are  Unavailable seats are full");
            }
            
//            Play play = getPlayObjcetMapper(response.getBody().getBody().toString());
            
            //logger.info(play.toString());
			
			
            
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to update Booking", ex.getCause());
		}
		catch(Exception ex) {
			throw new BookingServiceDaoException(ex.getMessage(), ex.getCause());
		}


	}

	@Override
	@Transactional
	public String deleteBooking(int bookingId,HttpServletRequest request) throws BookingServiceDaoException {
		try {
			logger.info("Entered into booking services");
			
			
			Optional<Booking> booking = bookingRepository.findById(bookingId);
			if(!booking.isPresent()) {
				throw new BookingServiceDaoException("No Booking Data found for the id - "+bookingId);
			}
			
			logger.info(booking.get().toString());
			
			String authorization = request.getHeader("Authorization");
           
            
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Authorization", authorization);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

 

            HttpEntity requestEntity = new HttpEntity(requestHeaders);
            
            APISuccessResponse response = restTemplate.exchange(
            		playUrl+booking.get().getPlayId()
            		,HttpMethod.GET
            		,requestEntity
            		,APISuccessResponse.class).getBody();
            
                Play play=objectMapper.convertValue(response.getBody(), new TypeReference<Play>() {});
            
                play.setSeatsAvailable(play.getSeatsAvailable()+booking.get().getSeatCount());
            	            	
            	HttpEntity<Play> updateRequest =  new HttpEntity<>(play,requestHeaders);
            	
            	APISuccessResponse apiResponse = restTemplate.exchange(
                		playUrl
                		,HttpMethod.PUT
                		,updateRequest
                		,APISuccessResponse.class).getBody();
            	
            	logger.info(apiResponse.toString());
            	
            	bookingRepository.deleteById(bookingId);
            	
            	return "Successfully deleted booking of id - "+bookingId;
            
//            Play play = getPlayObjcetMapper(response.getBody().getBody().toString());
            
            //logger.info(play.toString());
			
			
            
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to Delete Booking", ex.getCause());
		}
		catch(Exception ex) {
			throw new BookingServiceDaoException(ex.getMessage(), ex.getCause());
		}


	}


	public Play getPlayObjcetMapper(String response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(response, Play.class);
	}

}
