package auth.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.json.*;


/**
 * Servlet implementation class LoginCallback
 */
@WebServlet("/LoginCallback")
public class LoginCallback extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Token EMPTY_TOKEN = null;
    private String RESPONSE_TOKEN = null;
    private static final String apiKey = "222749897854560";
	private static final String apiSecret = "833b20024613e602fd212b53cf0863e2";
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCallback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OAuthService service = new ServiceBuilder()
								        .provider(FacebookApi.class)
								        .apiKey(apiKey)
								        .apiSecret(apiSecret)
								        .callback("http://localhost:8080/AuthUsingScribe/LoginCallback")
								        .build();
		RESPONSE_TOKEN = request.getParameter("code");
		Verifier verifier = new Verifier(RESPONSE_TOKEN);
		Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
		System.out.println("(if your curious it looks like this: " + accessToken + " )");
		OAuthRequest req = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
	    service.signRequest(accessToken, req);
	    Response res = req.send();
	    //System.out.println("Got it! Lets see what we found...");
	    //System.out.println();
	    //System.out.println(res.getCode());
	    System.out.println(res.getBody());
	    JSONObject json = null;
	    try {
			json = new JSONObject(res.getBody());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    response.setContentType("application/json");
	 // Get the printwriter object from response to write the required json object to the output stream      
	 PrintWriter out = response.getWriter();
	 // Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	 out.print(json);
	 out.flush();
	    //System.out.println();
	    //System.out.println("Thats it man! Go and build something awesome with Scribe! :)");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
