package Tasks;

import java.util.concurrent.BlockingQueue;

import resources.XmlTransformer;


//Divide un mensaje de entrada formado por una lista de elementos en tantos mensajes como elementos tenga

public class Splitter implements Task{

	private BlockingQueue<String> entryPort;
	private BlockingQueue<String> exitPort;
	private XmlTransformer transformer;
	
	private String xsltFilePath;
	
	
	
	
}
