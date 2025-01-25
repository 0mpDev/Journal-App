package com._mp.restAPI.service;

import com._mp.restAPI.entity.JournalEntry;
import com._mp.restAPI.entity.User;
import com._mp.restAPI.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;  // Set the userService in constructor
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);  // Use instance variable, not class name
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}

//@Service
//public class JournalEntryService {
//
//
//    private JournalEntryRepository journalEntryRepository;
//
//    @Autowired
//    private UserService userService;
//
//    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
//        this.journalEntryRepository = journalEntryRepository;
//    }
//
//
//    public void saveEntry(JournalEntry journalEntry, String userName){
//        User user = userService.findByUserName(userName);
//        journalEntry.setDate(LocalDateTime.now());
//        JournalEntry saved = journalEntryRepository.save(journalEntry);
//        user.getJournalEntries().add(saved);
//        userService.saveEntry(user);
//    }
//
//    public void saveEntry(JournalEntry journalEntry){
//        JournalEntryRepository.save(journalEntry);
//    }
//
//
//    public List<JournalEntry> getAll(){
//        return journalEntryRepository.findAll();
//    }
//
//    public Optional<JournalEntry> findById(ObjectId id){
//        return journalEntryRepository.findById(id);
//    }
//
//    public void deleteById(ObjectId id, String userName){
//        User user = userService.findByUserName(userName);
//        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
//        userService.saveEntry(user);
//        journalEntryRepository.deleteById(id);
//    }
//
//}
