package Tasks;

import java.util.concurrent.BlockingQueue;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import resources.XmlTransformer;

//Transforma el cuerpo de un mensaje de un formato a otro

public class Translator implements ITask{

	private XmlTransformer transformer;
	
	private String xsltFilePath;

	public Translator() {}
	
	
	public Translator(BlockingQueue<String> entryPort, BlockingQueue<String> exitPort, String xsltFilePath) {
		super();
	
		this.xsltFilePath = xsltFilePath;
		transformer = new XmlTransformer();
	}
	
	
	public void processMessages() {
        while (true) { 
            try {
               
                Document xmlInput ;
                
               // String transformedOutput = transformer.ApplyXSLT(xmlInput, xsltFilePath);

                

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                System.out.println("El proceso fue interrumpido.");
                break; 
            } catch (TransformerException e) {          
                System.err.println("Error en la transformación: " + e.getMessage());
             }
        }
    }
public void run() {
		
	}
	
} 