package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.UnAuthenticationException;
import codesquad.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("qnaService")
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    @Resource(name = "questionRepository")
    private QuestionRepository questionRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Question create(User loginUser, Question question) {
        question.writeBy(loginUser);
        log.debug("question : {}", question);
        return questionRepository.save(question);
    }

    public Question findById(long id) throws Exception {
        return questionRepository.findById(id)
                .orElseThrow(UnAuthenticationException::new);
    }

    @Transactional
    public Question update(User loginUser, long id, Question updatedQuestion) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);

        return question.update(loginUser, updatedQuestion);
    }

    @Transactional
    public void deleteQuestion(User loginUser, long questionId) throws CannotDeleteException {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new CannotDeleteException("No Such question"));
        question.delete(loginUser);
    }

    public List<Question> findAll() {
        return questionRepository.findByDeleted(false);
    }


    public Answer addAnswer(User loginUser, long questionId, String contents) throws Exception {
        Question question = findById(questionId);
        Answer answer = new Answer(loginUser, contents);
        question.addAnswer(answer);
        answer.toQuestion(question);
        return answerRepository.save(answer);
    }

    public void deleteAnswer(User loginUser, long id) throws Exception {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        answer.delete(loginUser);
    }
}
