package codesquad.web;

import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.HtmlFormDataBuilder;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class QnaAcceptanceTest extends AcceptanceTest {

	@Autowired
	private QuestionRepository questionRepository;

	private HtmlFormDataBuilder formDataBuilder;
	private ResponseEntity<String> response;
	@Before
	public void setUp() {
		formDataBuilder = HtmlFormDataBuilder.urlEncodedForm();
	}

	@Test
	public void create() {
		User loginUser = defaultUser();
		addSampleQuestionData();
		response = basicAuthTemplate(loginUser)
				.postForEntity("/questions", formDataBuilder.build(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(response.getHeaders().getLocation().getPath()).startsWith("/questions");
	}

	@Test
	public void create_no_login() {
		addSampleQuestionData();
		response = template().postForEntity("/questions", formDataBuilder.build(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}


	@Test
	public void delete(){
		formDataBuilder.
	}

	@Test
	public void delete_no_authenticated() {

	}

	@Test
	public void update() {

	}

	@Test
	public void update_no_authenticated() {

	}

	@Test
	public void read() {

	}

	@Test
	public void list() {

	}

	private void addSampleQuestionData() {
		formDataBuilder
				.addParameter("title", "title")
				.addParameter("contents", "contents");
	}

}
