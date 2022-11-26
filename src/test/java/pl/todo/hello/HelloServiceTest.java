package pl.todo.hello;

import org.junit.Test;
import pl.todo.lang.Lang;
import pl.todo.lang.LangRepository;

import java.net.http.HttpRequest;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {
    private static final String WELCOME = "Hello";
    private static final String FALBACK_ID_WELCOME = "Hola";

    @Test
    public void test_nullName_prepareGreeting_returnsGreetingWithFallbackName() throws Exception{
        //given

        var mockRepository = alwaysReturningHelloRepository();
        HelloService SUT = new HelloService(mockRepository);
        //when
        var result = SUT.prepareGreeting(null, -1);
        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!",result);
    }

    @Test
    public void test_name_prepareGreeting_returnsGreetingWithName() throws Exception{
        //given
        var mockRepository = alwaysReturningHelloRepository();
        HelloService SUT = new HelloService(mockRepository);
        String name = "test";
        //when
        var result = SUT.prepareGreeting(name,-1);
        //then
        assertEquals(WELCOME + " " + name + "!",result);
    }
    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() throws Exception{
        //given
        var mockRepository = fallbackLangIdRepository();
        HelloService SUT = new HelloService(mockRepository);
        String name = "test";
        //when
        var result = SUT.prepareGreeting(null,null);
        //then
        assertEquals(FALBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!",result);
    }
//    @Test
//    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() throws Exception{
//        //given
//        var mockRepository = fallbackLangIdRepository();
//        HelloService SUT = new HelloService(mockRepository);
//        String name = "test";
//        //when
//        var result = SUT.prepareGreeting(null,"abc");
//        //then
//        assertEquals(FALBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!",result);
//    }
    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() throws Exception{
        //given
        var mockRepository = new LangRepository(){
            @Override
            public Optional<Lang> finfById(Integer id) {
                return Optional.empty();
            }
        };
        HelloService SUT = new HelloService(mockRepository);
        String name = "test";
        //when
        var result = SUT.prepareGreeting(null,null);
        //then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME +  "!",result);
    }


    public LangRepository fallbackLangIdRepository() {
        return new LangRepository(){
            @Override
            public Optional<Lang> finfById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null,FALBACK_ID_WELCOME,null));
                }
                return Optional.empty();
            }
        };
    }

    public LangRepository alwaysReturningHelloRepository() {
        return new LangRepository(){
            @Override
            public Optional<Lang> finfById(Integer id) {
                return Optional.of(new Lang(null, WELCOME,null));
            }
        };
    }
}
