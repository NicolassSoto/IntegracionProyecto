package Tasks;

import java.util.concurrent.BlockingQueue;

import javax.xml.transform.TransformerException;

import resources.XmlTransformer;

//Transforma el cuerpo de un mensaje de un formato a otro

public class Translator implements Task{

	private BlockingQueue<String> entryPort;
	private BlockingQueue<String> exitPort;	
	private XmlTransformer transformer;
	
	private String xsltFilePath;

	public Translator() {}
	
	
	public Translator(BlockingQueue<String> entryPort, BlockingQueue<String> exitPort, String xsltFilePath) {
		super();
		this.entryPort = entryPort;
		this.exitPort = exitPort;
		this.xsltFilePath = xsltFilePath;
		transformer = new XmlTransformer();
	}
	
	
	public void processMessages() {
        while (true) { 
            try {
               
                String xmlInput = entryPort.take(); 
                
                String transformedOutput = transformer.ApplyXSLT(xmlInput, xsltFilePath);

                exitPort.put(transformedOutput); 

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                System.out.println("El proceso fue interrumpido.");
                break; 
            } catch (TransformerException e) {          
                System.err.println("Error en la transformación: " + e.getMessage());
             }
        }
    }
	
} 