package edu.byu.cs.sonar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomFileReaderTest {

    private CustomFileReader sut;

    @BeforeEach
    void setUp() {
        sut = new CustomFileReader("readMe1.txt");
    }

    @Test
    void testHowManyWordsInFile() throws FileNotFoundException {
        assertEquals(4, sut.howManyWordsInFile(), "There should be 4 words in readMe1.txt");
    }

    @Test
    void testReturnThatWord() throws FileNotFoundException {
        assertEquals("I", sut.returnThatWord(1), "The first word should be I in readMe1.txt");
    }

    @Test
    void testFindNewWord() throws FileNotFoundException {
        sut.findNewWord("C");
        assertEquals("Computer ", sut.getNewSentence(), "Computer should be found when C queried");
    }

    @Test
    void testGetNewSentence() {
        assertEquals("", sut.getNewSentence(), "New sentence should be empty initially");
    }

    @Test
    void setNewSentence() {
        String betterSentence = "New Sentence.";
        sut.setNewSentence(betterSentence);
        assertEquals(betterSentence, sut.getNewSentence(), "Sentence should be updated by the setter");
    }

    @Test
    void testMissingFileThrows() {
        CustomFileReader missing = new CustomFileReader("doesNotExist.txt");
        assertThrows(FileNotFoundException.class, missing::howManyWordsInFile,
                "Reading a missing file should throw");
    }

    @Test
    void testToString() throws FileNotFoundException {
        sut.setNewSentence("Hello");
        sut.howManyWordsInFile();
        assertEquals("Hello 4", sut.toString(), "toString should show sentence and count");
    }

    @Test
    void testEqualsAndHashCode() throws FileNotFoundException {
        CustomFileReader same = new CustomFileReader("readMe1.txt");
        CustomFileReader alias = sut;
        assertEquals(alias, sut, "A reader should equal itself");
        assertEquals(sut, same, "Readers with the same state should be equal");
        assertEquals(sut.hashCode(), same.hashCode(), "Equal readers should have equal hash codes");

        assertNotEquals(null, sut, "A reader should not equal null");
        Object otherType = "readMe1.txt";
        assertNotEquals(otherType, sut, "A reader should not equal another type");

        CustomFileReader otherPath = new CustomFileReader("readMe2.txt");
        assertNotEquals(sut, otherPath, "Readers with different paths should differ");

        CustomFileReader otherSentence = new CustomFileReader("readMe1.txt");
        otherSentence.setNewSentence("different");
        assertNotEquals(sut, otherSentence, "Readers with different sentences should differ");

        CustomFileReader otherCount = new CustomFileReader("readMe1.txt");
        otherCount.howManyWordsInFile();
        assertNotEquals(sut, otherCount, "Readers with different counts should differ");
    }
}
