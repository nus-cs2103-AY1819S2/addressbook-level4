package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.PlayerStatistics;

@JsonRootName(value = "statistics")
class JsonSerializableStatistics {

    private String hitCount = "";
    private String missCount="";
    private String movesMade="";
    private String enemyShipsDestroyed="";
    private String attackCount="";

    /**
     * Converts a given {@code PlayerStatistics into this class for Jackson use.
     *
     * @param statisticsData future changes to this will not affect the created {@code JsonSerializableStatistics}.
     */

//    public JsonSerializableStatistics(PlayerStatistics statisticsData) {
//        //added the adpated person to a list
//        JsonSerializableStatistics(String.valueOf(statisticsData.getHitCount()),
//                String.valueOf(statisticsData.getMissCount()),
//                String.valueOf(statisticsData.getMovesMade()),
//                String.valueOf(statisticsData.getEnemyShipsDestroyed()),
//                String.valueOf(statisticsData.getAttacksMade()));
//
////        @JsonProperty("hitCount") hitCount = String.valueOf(statisticsData.getHitCount());
////        @JsonProperty("missCount") this.missCount = String.valueOf(statisticsData.getMissCount());
////        @JsonProperty("movesMade") this.movesMade = String.valueOf(statisticsData.getMovesMade());
////        @JsonProperty("enemyShipsDestroyed") this.enemyShipsDestroyed = String.valueOf(statisticsData.getEnemyShipsDestroyed());
////        @JsonProperty("attackCount") this.attackCount = String.valueOf(statisticsData.getAttacksMade());
//    }

    @JsonCreator
    public JsonSerializableStatistics(@JsonProperty("hitCount") String hitCount, @JsonProperty("missCount") String missCount,
                     @JsonProperty("movesMade") String movesMade, @JsonProperty("enemyShipsDestroyed") String enemyShipsDestroyed,
                     @JsonProperty("attackCount") String attackCount) {
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.movesMade = movesMade;
        this.enemyShipsDestroyed = enemyShipsDestroyed;
        this.attackCount = attackCount;

    }

//    public JsonSerializableStatistics(String hitCount, @JsonProperty("missCount") String missCount,
//                                      String movesMade, @JsonProperty("enemyShipsDestroyed") String enemyShipsDestroyed,
//                                      @JsonProperty("attackCount" String attackCount)) {
//        this.hitCount = hitCount;
//        this.missCount = missCount;
//        this.movesMade = movesMade;
//        this.enemyShipsDestroyed = enemyShipsDestroyed;
//        this.attackCount = attackCount;
//
//    }

    /**
     * Converts this address book into the model's {@code MapGrid} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PlayerStatistics toModelType() throws IllegalValueException {
        PlayerStatistics playerStats = new PlayerStatistics();
        playerStats.setHitCount(Integer.parseInt(this.hitCount));
        playerStats.setMissCount(Integer.parseInt(this.missCount));
        playerStats.setMovesMade(Integer.parseInt(this.movesMade));
        playerStats.setEnemyShipsDestroyed(Integer.parseInt(this.enemyShipsDestroyed));
        playerStats.setAttackCount(Integer.parseInt(this.attackCount));
        // add back into this playerStats obj
        return playerStats;
    }

}
