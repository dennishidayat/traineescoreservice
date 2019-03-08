package com.enigma.task.traineescore;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.task.traineescore.dao.TraineeScoreDao;
import com.enigma.task.traineescore.dto.CommonResponse;
import com.enigma.task.traineescore.dto.TraineeScoreDto;
import com.enigma.task.traineescore.exception.CustomException;
import com.enigma.task.traineescore.model.TraineeScore;

@RestController
@RequestMapping("/traineescores")
@SuppressWarnings("rawtypes")
public class TraineeScoreController {
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Autowired
	public TraineeScoreDao traineeScoreDao;
	
	@GetMapping(value="/{scoreid}")
	public CommonResponse<TraineeScoreDto> getById(@PathVariable("scoreid") String scoreId) throws CustomException {
		
		try {
			TraineeScore traineeScore = traineeScoreDao.getById(Integer.parseInt(scoreId));
			
			return new CommonResponse<TraineeScoreDto>(modelMapper.map(traineeScore, TraineeScoreDto.class));
		} catch (CustomException e) {
			return new CommonResponse<TraineeScoreDto>("01", e.getMessage());
		} catch (NumberFormatException e) {
			return new CommonResponse<TraineeScoreDto>("06", "input must be a number");
		} catch (Exception e) {
			return new CommonResponse<TraineeScoreDto>("06", e.getMessage());
		}
	}
	
	@PostMapping(value="")
	public CommonResponse<TraineeScoreDto> update(@RequestBody TraineeScoreDto traineeScoreDto) throws CustomException {
		try {
			TraineeScore traineeScore = modelMapper.map(traineeScoreDto, TraineeScore.class);
			traineeScore.setScoreId(0);
			traineeScore = traineeScoreDao.save(traineeScore);
			
			return new CommonResponse<TraineeScoreDto>(modelMapper.map(traineeScore, TraineeScoreDto.class));
		} catch (CustomException e) {
			return new CommonResponse<TraineeScoreDto>("14", "trainee not found");
		} catch (NumberFormatException e) {
			return new CommonResponse<TraineeScoreDto>();
		} catch (Exception e) {
			return new CommonResponse<TraineeScoreDto>();
		}
	}
	
	@PutMapping(value="")
	public CommonResponse<TraineeScoreDto> insert(@RequestBody TraineeScoreDto traineeScoreDto) throws CustomException {
		try {
			TraineeScore checkTraineeScore = traineeScoreDao.getById(traineeScoreDto.getScoreId());
			if (checkTraineeScore == null) {
				return new CommonResponse<TraineeScoreDto>("01", "scored id not found");
			}
			if (traineeScoreDto.getActiveFlag() != null) {
				checkTraineeScore.setActiveFlag(traineeScoreDto.getActiveFlag());
			}
			if (traineeScoreDto.getBootcampMaterial() != null) {
				checkTraineeScore.setBootcampMaterial(traineeScoreDto.getBootcampMaterial());
			}
			if (traineeScoreDto.getScoreKnowledge() != null) {
				checkTraineeScore.setScoreKnowledge(traineeScoreDto.getScoreKnowledge());
			}
			if (traineeScoreDto.getScoreProactiveness() != null) {
				checkTraineeScore.setScoreProactiveness(traineeScoreDto.getScoreProactiveness());
			}
			if (traineeScoreDto.getScoreTask() != null) {
				checkTraineeScore.setScoreTask(traineeScoreDto.getScoreTask());
			}
			if (traineeScoreDto.getNotes() != null) {
				checkTraineeScore.setNotes(traineeScoreDto.getNotes());
			}
			if (traineeScoreDto.getTrainee() != null) {
				checkTraineeScore.setTrainee(traineeScoreDto.getTrainee());
			}
			checkTraineeScore = traineeScoreDao.save(checkTraineeScore);
			
			return new CommonResponse<TraineeScoreDto>(modelMapper.map(checkTraineeScore, TraineeScoreDto.class));
		} catch (CustomException e) {
			return new CommonResponse<TraineeScoreDto>("14", "trainee not found");
		} catch (NumberFormatException e) {
			return new CommonResponse<TraineeScoreDto>();
		} catch (Exception e) {
			return new CommonResponse<TraineeScoreDto>();
		}
	}
	
	@DeleteMapping(value="/{scoreid}")
	public CommonResponse<TraineeScoreDto> delete(@PathVariable("scoreid") String scoreId) throws CustomException {
		try {
			TraineeScore traineeScore = traineeScoreDao.getById(Integer.parseInt(scoreId));
			traineeScoreDao.delete(traineeScore);
			return new CommonResponse<TraineeScoreDto>();
			
		} catch (CustomException e) {
			return new CommonResponse<TraineeScoreDto>("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse<TraineeScoreDto>("06", e.getMessage());
		}
	}
	
	@GetMapping(value="")
	public CommonResponse<List<TraineeScoreDto>> getList(@RequestParam(name="list", defaultValue="") String trainee_Scores) throws CustomException {
		try {
			List<TraineeScore> traineeScores = traineeScoreDao.getList();
			return new CommonResponse<List<TraineeScoreDto>>(traineeScores.stream()
					.map(trnScore -> modelMapper.map(trnScore, TraineeScoreDto.class))
					.collect(Collectors.toList()));
		} catch (CustomException e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping(value="/active")
	public CommonResponse<List<TraineeScoreDto>> getListByActiveFlag(@RequestParam(name="list", defaultValue="") String trainee_Scores) throws CustomException {
		try {
			List<TraineeScore> traineeScores = traineeScoreDao.getList();
			return new CommonResponse<List<TraineeScoreDto>>(traineeScores.stream()
					.map(trnScore -> modelMapper.map(trnScore, TraineeScoreDto.class))
					.collect(Collectors.toList()));
		} catch (CustomException e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
}
