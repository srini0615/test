package com.tcs.fresco;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Main {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        System.out.println("Consolidate Test report xml data----------------");
        
        //Get all result xml files in the directory
           File directory = new File("./target/surefire-reports/");
           File[] fList = directory.listFiles();
           ArrayList <File> fListMod=new ArrayList<File>();
           for (File file : fList){
               if (file.isFile() && file.getName().endsWith(".xml")){
                   fListMod.add(file);
                   if(file.getName().equals("final1.xml")) {
                       fListMod.remove(file);
                       file.delete();
                   }
               }
           }
           
        
    File fXmlFile = fListMod.get(0);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
    int test=Integer.valueOf(doc.getDocumentElement().getAttribute("tests"));
    int errors=Integer.valueOf(doc.getDocumentElement().getAttribute("errors"));
    int skipped=Integer.valueOf(doc.getDocumentElement().getAttribute("skipped"));
    int failures=Integer.valueOf(doc.getDocumentElement().getAttribute("failures"));
    Double time=Double.valueOf(doc.getDocumentElement().getAttribute("time"));
    
    
    
    
    
    for (int i=1;i<fListMod.size();i++) {
        File fXmlFile1 =fListMod.get(i);
        System.out.println("xml file name--"+fXmlFile1.getName());
        DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
        Document doc1 = dBuilder1.parse(fXmlFile1);
        int test1=Integer.valueOf(doc1.getDocumentElement().getAttribute("tests"));
        int errors1=Integer.valueOf(doc1.getDocumentElement().getAttribute("errors"));
        int skipped1=Integer.valueOf(doc1.getDocumentElement().getAttribute("skipped"));
        int failures1=Integer.valueOf(doc1.getDocumentElement().getAttribute("failures"));
        Double time1=Double.valueOf(doc1.getDocumentElement().getAttribute("time"));
        
        test=test+test1;
        errors=errors+errors1;
        skipped=skipped+skipped1;
        failures=failures+failures1;
        time=time+time1;
        
        

        
        for(int itCnt=0; itCnt<doc1.getElementsByTagName("testcase").getLength();itCnt++) {

            Element element=(Element)doc.createElement("testcase");
            doc.getDocumentElement().appendChild(element);
            Node nNode = doc1.getElementsByTagName("testcase").item(itCnt);
            Element eElement = (Element) nNode;

            if(eElement.getElementsByTagName("failure")!=null && eElement.getElementsByTagName("failure").getLength()>0) {
            
                Element element1=(Element)doc.createElement("failure");
                element.appendChild(element1);
                element1.setAttribute(eElement.getElementsByTagName("failure").item(0).getAttributes().item(0).getNodeName(), eElement.getElementsByTagName("failure").item(0).getAttributes().item(0).getTextContent());
                if(eElement.getElementsByTagName("failure").item(0).hasChildNodes()) {
                    //Node nNode1=eElement.getElementsByTagName("failure").item(0).getFirstChild();
                    Element eElement1 = (Element) nNode;
                    Node cdata = doc.createCDATASection(eElement1.getTextContent());
                    element1.appendChild(cdata);
                }
            }
            if(eElement.getElementsByTagName("system-out")!=null && eElement.getElementsByTagName("system-out").getLength()>0) {
                //Node nNode1=eElement.getElementsByTagName("system-out").item(0).getFirstChild();
                Element eElement1 = (Element) nNode;
                Node cdata = doc.createCDATASection(eElement1.getTextContent());
                Element element1=(Element)doc.createElement("system-out");
                element.appendChild(element1);
                element1.appendChild(cdata);
            }
            if(eElement.getElementsByTagName("error")!=null && eElement.getElementsByTagName("error").getLength()>0) {
                
                Element element1=(Element)doc.createElement("error");
                element.appendChild(element1);
                element1.setAttribute(eElement.getElementsByTagName("error").item(0).getAttributes().item(0).getNodeName(), eElement.getElementsByTagName("error").item(0).getAttributes().item(0).getTextContent());
                if(eElement.getElementsByTagName("error").item(0).hasChildNodes()) {
                    //Node nNode1=eElement.getElementsByTagName("failure").item(0).getFirstChild();
                    Element eElement1 = (Element) nNode;
                    Node cdata = doc.createCDATASection(eElement1.getTextContent());
                    element1.appendChild(cdata);
                }
            }
            
            for(int itCnt1=0; itCnt1<doc1.getElementsByTagName("testcase").item(itCnt).getAttributes().getLength();itCnt1++) {
                
                element.setAttribute(String.valueOf(doc1.getElementsByTagName("testcase").item(itCnt).getAttributes().item(itCnt1).getNodeName()), String.valueOf(doc1.getElementsByTagName("testcase").item(itCnt).getAttributes().item(itCnt1).getTextContent()));
                
    
                
            }
        }
        System.out.println("testcase : " + doc.getElementsByTagName("testcase").item(0).getAttributes().item(0));
        System.out.println("testcase : " + doc.getElementsByTagName("testcase").item(0).getAttributes().item(1));
        System.out.println("testcase : " + doc.getElementsByTagName("testcase").item(0).getAttributes().item(2));
        
    }
    
    
//    String [] str=Main1.getJacocoRep();
//    if(str!=null && str.length>0) {
//        int failuresJac= Integer.valueOf(str[0].trim());
//        int totalJac= Integer.valueOf(str[1].trim());
//        int successJac=totalJac-failuresJac;
//        for(int countr=0; countr<failuresJac;countr++) {
//            Element elementJac=(Element)doc.createElement("testcase");
//            elementJac.setAttribute("classname", "Missing Test Coverage");
//            elementJac.setAttribute("name", "Missing Test Coverage");
//            doc.getDocumentElement().appendChild(elementJac);
//            Element elementJac1=(Element)doc.createElement("failure");
//            elementJac.appendChild(elementJac1);
//            Node cdataJac = doc.createCDATASection("Missing Test Coverage");
//            elementJac1.appendChild(cdataJac);
//        }
//        for(int countr=0; countr<successJac;countr++) {
//            Element elementJac=(Element)doc.createElement("testcase");
//            elementJac.setAttribute("classname", "Test Coverage");
//            elementJac.setAttribute("name", "Test Coverage - Success");
//            doc.getDocumentElement().appendChild(elementJac);
//        }
//        test=test+totalJac;
//        failures=failures+failuresJac;
//    }
    
    doc=getcheckStyleRep(doc, test,failures);
    
    
//  doc.getDocumentElement().setAttribute("tests", String.valueOf(test));
    doc.getDocumentElement().setAttribute("errors", String.valueOf(errors));
    doc.getDocumentElement().setAttribute("skipped", String.valueOf(skipped));
//  doc.getDocumentElement().setAttribute("failures", String.valueOf(failures));
    
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File("./target/surefire-reports/final1.xml"));
    transformer.transform(source, result);
     File directory1 = new File("./target/surefire-reports/");
     //get all the files from a directory
     File[] fList1 = directory1.listFiles();
     ArrayList <File> fListMod1=new ArrayList<File>();
     for (File file1 : fList1){
         if (file1.isFile() && file1.getName().endsWith(".xml") && !(file1.getName().equals("final1.xml"))){
           fListMod1.add(file1);
//             file1.delete();
         }
     }
     System.out.println("Done");
    }
    
    public static Document getcheckStyleRep(Document doc , int test, int failures) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        System.out.println("Retrieving CheckStyle data----------------");
        
        String val=null;
        try {
	        File checkStyleRep = new File("./target/checkstyle-result.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document docCheck = dBuilder.parse(checkStyleRep);
	        int errCount=docCheck.getDocumentElement().getElementsByTagName("error").getLength();
	        int elementCount=0;
	        for(int itCnt=0;itCnt<docCheck.getDocumentElement().getElementsByTagName("error").getLength();itCnt++) {
	            Node n= docCheck.getDocumentElement().getElementsByTagName("error").item(itCnt);
	        }
	        if(errCount == 0) {
	            elementCount=0;
	        }
	        else if(errCount>0 && errCount<=10) {
	            elementCount=1;
	        }
	        else if(errCount>=11 && errCount<=20) {
	            elementCount=2;
	        }
	        else if(errCount>=21 && errCount<=30) {
	            elementCount=3;
	        }
	        else if(errCount>=31 && errCount<=40) {
	            elementCount=4;
	        }
	        else if(errCount>=41 && errCount<=50) {
	            elementCount=5;
	        }
	        else if(errCount>=51 && errCount<=60) {
	            elementCount=6;
	        }
	        else if(errCount>=61 && errCount<=70) {
	            elementCount=7;
	        }
	        else if(errCount>=71 && errCount<=80) {
	            elementCount=8;
	        }
	        else if(errCount>=81 && errCount<=90) {
	            elementCount=9;
	        }
	        else if(errCount>=91) {
	            elementCount=10;
	        }
	        for(int countr=0; countr<10-elementCount;countr++) {
	            Element elementJac=(Element)doc.createElement("testcase");
	            elementJac.setAttribute("classname", "Test CheckStyle");
	            elementJac.setAttribute("name", "Test CheckStyle");
	            doc.getDocumentElement().appendChild(elementJac);
	        }
	        for(int countr=0; countr<elementCount;countr++) {
	            Element elementJac=(Element)doc.createElement("testcase");
	            elementJac.setAttribute("classname", "CheckStyle Warnings");
	            elementJac.setAttribute("name", "CheckStyle Warnings");
	            doc.getDocumentElement().appendChild(elementJac);
	            Element elementJac1=(Element)doc.createElement("failure");
	            elementJac.appendChild(elementJac1);
	            Node cdataJac = doc.createCDATASection("CheckStyle Warnings");
	            elementJac1.appendChild(cdataJac);
	        }
	        test=test+10-elementCount;
	        failures=failures+elementCount;
	        doc.getDocumentElement().setAttribute("tests", String.valueOf(test));
	        doc.getDocumentElement().setAttribute("failures", String.valueOf(failures));
        }
        catch(Exception e) {
        	//Do nothing
        }
        return doc;
    }

}