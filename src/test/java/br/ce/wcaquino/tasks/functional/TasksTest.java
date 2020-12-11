package br.ce.wcaquino.tasks.functional;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        //Declarando local do chromedriver.exe
        System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Chrome Driver/chromedriver.exe");
        //instanciando objeto para utilização do chrome driver
        //WebDriver driver = new ChromeDriver();
        //Definindo o Desired Capabilities - qual browser vou usar
        ChromeOptions chrome = new ChromeOptions();
        //Definindo um remote driver, nesse caso tá local, mas na prática os testes são rodados geralmente em outra(s) maquinas(s) - nós
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.112:4444/wd/hub"), chrome);
        //Navegando até a página de adicionar tarefas
        driver.navigate().to("http://192.168.1.112:8001/tasks");
        //esperar 10 segundos
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add to do
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
            Assert.assertEquals("http://192.168.1.112:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarSeTiverErroDataPassada() throws MalformedURLException {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add to do
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
            Assert.assertEquals("http://192.168.1.112:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarSeTiverSemDescricao() throws MalformedURLException {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add to do
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
            Assert.assertEquals("http://192.168.1.112:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarSeNaoTiverData() throws MalformedURLException {
        //instanciando objeto para utilização do chrome driver
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add to do
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
            Assert.assertEquals("http://192.168.1.112:8001/tasks/save", driver.getCurrentUrl());
        } finally {
            //sai do Chrome
            driver.quit();
        }
    }
}
