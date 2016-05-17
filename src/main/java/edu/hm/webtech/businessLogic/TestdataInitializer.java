package edu.hm.webtech.businessLogic;

import edu.hm.webtech.association.Association;
import edu.hm.webtech.association.AssociationRepository;
import edu.hm.webtech.cloze.Cloze;
import edu.hm.webtech.cloze.ClozeRepository;
import edu.hm.webtech.exercise.BloomLevel;
import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.multipleChoice.MultipleChoice;
import edu.hm.webtech.multipleChoice.MultipleChoiceRepository;
import edu.hm.webtech.singleChoice.SingleChoice;
import edu.hm.webtech.singleChoice.SingleChoiceRepository;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Fabian on 17.05.2016.
 */
@Controller
public class TestdataInitializer {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    SingleChoiceRepository singleChoiceRepository;
    @Autowired
    MultipleChoiceRepository multipleChoiceRepository;
    @Autowired
    AssociationRepository associationRepository;
    @Autowired
    ClozeRepository clozeRepository;

    @RequestMapping(path = "/initdata", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> createTestdata() {
        try {
            createTopics();
            createSingleChoice();
            createMultipleChoice();
            createAssociation();
            createCloze();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Something went wrong. Maybe the testdata was already initialized?", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    private String[] topics = new String[]{"Java", "GUI", "C", "Software Engineering", "Scrum", "Web-Techniken", "Lesen"};

    private void createTopics() {
        Stream.of(topics).forEach(s -> topicRepository.save(new Topic(s)));
    }

    private void createSingleChoice() {
        List<SingleChoice> singleChoiceList = new ArrayList<>();

        singleChoiceList.add(new SingleChoice(
                "Richtig!",
                new HashSet<>(Arrays.asList(new String[]{"Falsch", "Ganz Falsch", "Besonders Falsch"})),
                "Dies ist eine wichtige Java-Frage",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.ANALYSIEREN, topicRepository.findTopicByName(topics[0]).getId())
                }))
        ));

        singleChoiceList.add(new SingleChoice(
                "Nein",
                new HashSet<>(Arrays.asList(new String[]{"Ja", "Natürlich"})),
                "Ist es schön eine GUI in C zu schreiben?",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.KREIEREN, topicRepository.findTopicByName(topics[1]).getId()),
                        new TopicBloomLevel(BloomLevel.BEWERTEN, topicRepository.findTopicByName(topics[2]).getId())
                }))
        ));

        singleChoiceRepository.save(singleChoiceList);
    }

    private void createMultipleChoice() {
        List<MultipleChoice> multipleChoiceList = new ArrayList<>();

        multipleChoiceList.add(new MultipleChoice(
                new HashSet<>(Arrays.asList(new String[]{"Gut", "Hilfreich"})),
                new HashSet<>(Arrays.asList(new String[]{"Schlecht", "Nicht mein ding", "Was ist Scrum?"})),
                "Scrum ist...",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.BEWERTEN, topicRepository.findTopicByName(topics[4]).getId())
                }))
        ));

        multipleChoiceList.add(new MultipleChoice(
                new HashSet<>(Arrays.asList(new String[]{"Schmolymer", "Ungalar", "WYSIWYG"})),
                new HashSet<>(Arrays.asList(new String[]{"Polymer", "Angular"})),
                "Welche Frameworks gibt es nicht?",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.BEWERTEN, topicRepository.findTopicByName(topics[5]).getId()),
                        new TopicBloomLevel(BloomLevel.BEWERTEN, topicRepository.findTopicByName(topics[1]).getId())
                }))
        ));

        multipleChoiceRepository.save(multipleChoiceList);
    }

    private void createAssociation() {
        HashMap<String, Set<String>> associationsmap = new HashMap<>();
        Set<String> answers = new HashSet<>();
        answers.add("A");
        answers.add("B");
        answers.add("C");
        associationsmap.put("Buchstaben", answers);
        Set<String> answers2 = new HashSet<>();
        answers2.add("1");
        answers2.add("2");
        answers2.add("3");
        associationsmap.put("Zahlen", answers2);

        associationRepository.save(new Association(
                "Verbinden Sie die Gruppen.",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.ANALYSIEREN, topicRepository.findTopicByName(topics[6]).getId()),
                })),
                associationsmap
        ));
    }

    private void createCloze() {
        List<Cloze> clozeList = new ArrayList<>();

        clozeList.add(new Cloze(
                "Füllen Sie die Lücken aus.",
                "Ich bin ein Text mit <Lücken>. Man nennt mich einen Lückentext. Dieser hier ist aber eher <unspannend>.",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.ANWENDEN, topicRepository.findTopicByName(topics[6]).getId()),
                }))
        ));

        clozeList.add(new Cloze(
                "Füllen Sie die Lücken aus.",
                "Stoff für Diskussionen bietet gerne der CORS-Filter. Dieser muss nämlich <Server>- und nicht <Client>-seitig aktiviert werden.",
                new HashSet<>(Arrays.asList(new TopicBloomLevel[]{
                        new TopicBloomLevel(BloomLevel.ANALYSIEREN, topicRepository.findTopicByName(topics[5]).getId()),
                }))
        ));

        clozeRepository.save(clozeList);
    }
}
