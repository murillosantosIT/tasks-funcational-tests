package br.ce.wcaquino.tasks.functional;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao(){
        //Declarando local do chromedriver.exe
        System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Chrome Driver/chromedriver.exe");
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = new ChromeDriver();
        //Navegando até a página de adicionar tarefas
        driver.navigate().to("http:/localhost:8001/tasks");
        //esperar 10 segundos
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add Todo
            WebElement btnAdd = driver.findElement(By.id("addTodo"));
            btnAdd.click();

            //escrever a descrição
            WebElement campoDescricao = driver.findElement(By.id("task"));
            campoDescricao.sendKeys("Teste via Selenium");

            //escrever a data
            WebElement campoData = driver.findElement(By.id("dueDate"));
            campoData.sendKeys("10/02/2021");

            //clicar em salvar
            WebElement btnSalvar = driver.findElement(By.id("saveButton"));
            btnSalvar.click();

            //validar mensagem de sucesso
            String Mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", Mensagem);

            //validar url final
            Assert.assertEquals("http://localhost:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarSeTiverErroDataPassada() {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add Todo
            WebElement btnAdd = driver.findElement(By.id("addTodo"));
            btnAdd.click();

            //escrever a descrição
            WebElement campoDescricao = driver.findElement(By.id("task"));
            campoDescricao.sendKeys("Teste via Selenium");

            //escrever a data no passado
            WebElement campoData = driver.findElement(By.id("dueDate"));
            campoData.sendKeys("10/02/2019");

            //clicar em salvar
            WebElement btnSalvar = driver.findElement(By.id("saveButton"));
            btnSalvar.click();

            //validar mensagem de erro
            String Mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", Mensagem);

            //validar url final
            Assert.assertEquals("http://localhost:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarSeTiverSemDescricao() {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add Todo
            WebElement btnAdd = driver.findElement(By.id("addTodo"));
            btnAdd.click();

            //escrever a descrição vazia
            WebElement campoDescricao = driver.findElement(By.id("task"));
            campoDescricao.sendKeys("");

            //escrever a data
            WebElement campoData = driver.findElement(By.id("dueDate"));
            campoData.sendKeys("10/02/2021");

            //clicar em salvar
            WebElement btnSalvar = driver.findElement(By.id("saveButton"));
            btnSalvar.click();

            //validar mensagem de erro
            String Mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", Mensagem);

            //validar url final
            Assert.assertEquals("http://localhost:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarSeNaoTiverData() {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add Todo
            WebElement btnAdd = driver.findElement(By.id("addTodo"));
            btnAdd.click();

            //escrever a descrição
            WebElement campoDescricao = driver.findElement(By.id("task"));
            campoDescricao.sendKeys("Teste via Selenium");

            //nao escrever data
            WebElement campoData = driver.findElement(By.id("dueDate"));
            campoData.sendKeys("");

            //clicar em salvar
            WebElement btnSalvar = driver.findElement(By.id("saveButton"));
            btnSalvar.click();

            //validar mensagem de erro
            String Mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", Mensagem);

            //validar url final
            Assert.assertEquals("http://localhost:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }
}
