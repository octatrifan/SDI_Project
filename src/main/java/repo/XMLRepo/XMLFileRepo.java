package repo.XMLRepo;

import exception.ValidatorException;
import model.BaseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import repo.inMemoryRepo.InMemoryRepo;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * Abstract Class for XML Repo.
 *
 * @author Octa.
 */

public abstract class XMLFileRepo<ID, T extends BaseEntity<ID>> extends InMemoryRepo<ID, T> {
    protected String fileName;
    Document document;

    /**
     * Transforms Element into instance
     *
     * @param node - the element
     * @return T
     */
    abstract T createObject(Element node);

    /**
     * Transforms instance into Element
     *
     * @param obj - the instance
     * @return Element
     */
    abstract Element ElementFromObject(T obj);

    /**
     * Saves the given entity.
     *
     * @param entity - must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional<T> opt = super.save(entity);
        saveToFile();
        return opt;
    }

    /**
     * Delete the entity with the giden id (if exists)
     *
     * @param id must not be null.
     * @return an {@code Optional} - returns the entity if it was deleted, otherwise (e.g. id does not exist) null;
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id) {
        Optional<T> opt = super.delete(id);
        saveToFile();
        return opt;
    }

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was not updated (e.g. id does not exist), otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        Optional<T> opt = super.update(entity);
        saveToFile();
        return opt;
    }

    /**
     * Saves all entities to file
     */
    private void saveToFile() {
        try {
            Document currentDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = currentDocument.createElement("store");
            currentDocument.appendChild(root);
            this.entities.values().stream().forEach(obj -> {
                Element el = ElementFromObject(obj);
                Node el2 = currentDocument.importNode(el, true);
                root.appendChild(el2);
            });
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(currentDocument), new StreamResult(new FileOutputStream(fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the data from the file into memory
     */
    public void loadData() {
        try {
            Node root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item).forEach(node -> {
                        if (node instanceof Element) {
                            Element element = (Element) node;
                            T entity = createObject(element);
                            super.save(entity);
                        }
                    });
        } catch (Exception e) {
        }
    }

    /**
     * Adds a new child to the parent, with text content set
     *
     * @param document    - the document
     * @param parent      - the parent node,
     * @param tagName     - the name of its tag,
     * @param textContent - the text to be set
     */
    protected static void appendChildWithText(Document document, Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    /**
     * Gets the text from a given tag of an element.
     *
     * @param element - the element
     * @param tagName - the given tag
     * @return String
     */
    protected static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }


}