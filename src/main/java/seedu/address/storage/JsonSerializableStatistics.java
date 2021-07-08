package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * the JsonSerializableStatistics will format the statistics class in a json serializable way.
 * @author bos10
 */
@JsonRootName(value = "statistics")
public class JsonSerializableStatistics {

    private String hitCount = "";
    private String missCount = "";
    private String movesMade = "";
    private String enemyShipsDestroyed = "";
    private String attackCount = "";


    /**
     * Converts statistics information to Json property.
     * @param hitCount
     * @param missCount
     * @param movesMade
     * @param enemyShipsDestroyed
     * @param attackCount
     */
    @JsonCreator
    public JsonSerializableStatistics(@JsonProperty("hitCount") String hitCount,
        @JsonProperty("missCount") String missCount,
        @JsonProperty("movesMade") String movesMade,
        @JsonProperty("enemyShipsDestroyed") String enemyShipsDestroyed,
        @JsonProperty("attackCount") String attackCount) {
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.movesMade = movesMade;
        this.enemyShipsDestroyed = enemyShipsDestroyed;
        this.attackCount = attackCount;

    }
    /**
     * Converts this json statistics data to the PlayerStatistics object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PlayerStatistics toModelType() {
        PlayerStatistics playerStats = new PlayerStatistics();
        playerStats.setHitCount(Integer.parseInt(this.hitCount));
        playerStats.setMissCount(Integer.parseInt(this.missCount));
        playerStats.setMovesMade(Integer.parseInt(this.movesMade));
        playerStats.setEnemyShipsDestroyed(Integer.parseInt(this.enemyShipsDestroyed));
        playerStats.setAttackCount(Integer.parseInt(this.attackCount));
        return playerStats;
    }

}
