package com.pakt;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CheckFile {

	public static void main(String argv[]) {

	   try {
		String filepath = "C:\\Program Files (x86)\\GeoServer 2.6.1\\data_dir\\styles\\default_point.sld";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		// Get the root element
		Node company = doc.getFirstChild();

		// Get the staff element , it may not working if tag has spaces, or
		// whatever weird characters in front...it's better to use
		// getElementsByTagName() to get it directly.
		// Node staff = company.getFirstChild();

		// Get the staff element by tag name directly
		Node staff = doc.getElementsByTagName("NamedLayer").item(0);

		// update staff attribute

		// loop the staff child node
		NodeList list = staff.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			
                   Node node = list.item(i);

		   // get the salary element, and update the value
		   if ("UserStyle".equals(node.getNodeName())) {
			   
			   NodeList list1 = node.getChildNodes();
			   for(int j = 0 ; j<list1.getLength() ; j++){
				   Node node1 = list1.item(j);
				   if ("FeatureTypeStyle".equals(node1.getNodeName())) {
					   
					   NodeList list2 = node1.getChildNodes();
					   for(int k = 0 ; k<list2.getLength() ; k++){
						   Node node2 = list2.item(k);
						   if ("Rule".equals(node2.getNodeName())) {
							   NodeList list3 = node2.getChildNodes();
							   for(int l = 0 ; l<list3.getLength() ; l++){
								   Node node3 = list3.item(l);
								   if ("PointSymbolizer".equals(node3.getNodeName())) {
									   NodeList list4 = node3.getChildNodes();
									   for(int m = 0 ; m<list4.getLength() ; m++){
										   Node node4 = list4.item(m);
										   if ("Graphic".equals(node4.getNodeName())) {
											   NodeList list5 = node4.getChildNodes();
											   for(int n = 0 ; n<list5.getLength() ; n++){
												   Node node5 = list5.item(n);
												   if ("Mark".equals(node5.getNodeName())) {
													   NodeList list6 = node5.getChildNodes();
													   for(int p = 0 ; p<list6.getLength() ; p++){
														   Node node6 = list6.item(p);
														   if ("Fill".equals(node6.getNodeName())) {
															   NodeList list7 = node6.getChildNodes();
															   for(int q = 0 ; q<list7.getLength() ; q++){
																   Node node7 = list7.item(q);
																   if ("CssParameter".equals(node7.getNodeName())) {
																	  node7.setTextContent("#FF0000");
																   }
															   }
														   }
													   }
												   }
											   }
										   }
									   }
								   }
							   }
						   }
					   }
				   }
			   }
		   }

		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);

		System.out.println("Done");

	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (TransformerException tfe) {
		tfe.printStackTrace();
	   } catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
	}
}