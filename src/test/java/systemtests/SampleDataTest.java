package systemtests;


public class SampleDataTest extends RestOrRantSystemTest {
//    /**
//     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
//     */
//    @Override
//    protected RestOrRant getInitialData() {
//        return null;
//    }
//
//    /**
//     * Returns a non-existent file location to force test app to load sample data.
//     */
//    @Override
//    protected Path getDataFileLocation() {
//        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
//        deleteFileIfExists(filePath);
//        return filePath;
//    }
//
//    /**
//     * Deletes the file at {@code filePath} if it exists.
//     */
//    private void deleteFileIfExists(Path filePath) {
//        try {
//            Files.deleteIfExists(filePath);
//        } catch (IOException ioe) {
//            throw new AssertionError(ioe);
//        }
//    }
//
//    @Test
//    public void addressBook_dataFileDoesNotExist_loadSampleData() {
//        Person[] expectedList = SampleDataUtil.getSamplePersons();
//        assertListMatching(getPersonListPanel(), expectedList);
//    }
}
