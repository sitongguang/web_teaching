package filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.List;

class CleanedRequest extends HttpServletRequestWrapper {
    public CleanedRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String parameter) {
        String str = super.getParameter(parameter);
        List<String> list = new ArrayList<String>();
        list.add("XX");
        for(String word : list){
            System.out.println(word);
            str = str.replaceAll(word, "*");
        }
        return str;
    }
}