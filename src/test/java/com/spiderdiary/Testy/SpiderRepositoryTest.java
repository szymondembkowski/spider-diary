package com.spiderdiary.Testy;

import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.TempForms.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpiderRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SpiderRepository spiderRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUserName("test_username");
    }

    @Test
    public void testFindAllSortedByMoltDate() {
        Spider spider1 = new Spider();

        Spider spider2 = new Spider();

        List<Spider> spiders = Arrays.asList(spider1, spider2);

        when(entityManager.createQuery(
                "SELECT s FROM Spider s WHERE s.user = :user ORDER BY s.moltDate DESC", Spider.class))
                .thenReturn(Mockito.mock(TypedQuery.class));
        when(spiderRepository.findAllSortedByMoltDate(testUser)).thenReturn(spiders);

        List<Spider> sortedSpiders = spiderRepository.findAllSortedByMoltDate(testUser);

        assertEquals(2, sortedSpiders.size());
    }

    @Test
    public void testSearchSpidersForUser() {

        String searchString = "tarantula";

        Spider spider1 = new Spider();
        spider1.setName("Tarantula");
        spider1.setSpecies("Theraphosidae");
        spider1.setUser(testUser);

        Spider spider2 = new Spider();
        spider2.setName("Black Widow");
        spider2.setSpecies("Latrodectus mactans");
        spider2.setUser(testUser);

        List<Spider> expectedResults = Arrays.asList(spider1, spider2);

        TypedQuery<Spider> typedQuery = Mockito.mock(TypedQuery.class);
        when(entityManager.createQuery(
                any(String.class), any(Class.class)))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedResults);

        List<Spider> searchedSpiders = spiderRepository.searchSpidersForUser(searchString, testUser);

        assertEquals(expectedResults.size(), searchedSpiders.size());
        assertEquals(expectedResults, searchedSpiders);
    }
}
