import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentInfo {

    private String uuid;
    private String displayName;
    private String description;
    private String developerName;
    private String[] characterTags;
    private String displayIcon;
    private String displayIconSmall;
    private String bustPortrait;
    private String fullPortrait;
    private String fullPortraitV2;
    private String killfeedPortrait;

    private String background;
    private String[] backgroundGradientColors;


    public String[] getCharacterTags() {
        return characterTags;
    }

    public void setCharacterTags(String[] characterTags) {
        this.characterTags = characterTags;
    }
    public String[] getBackgroundGradientColors() {
        return backgroundGradientColors;
    }

    public void setBackgroundGradientColors(String[] backgroundGradientColors) {
        this.backgroundGradientColors = backgroundGradientColors;
    }



    public String getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(String displayIcon) {
        this.displayIcon = displayIcon;
    }

    public String getDisplayIconSmall() {
        return displayIconSmall;
    }

    public void setDisplayIconSmall(String displayIconSmall) {
        this.displayIconSmall = displayIconSmall;
    }

    public String getBustPortrait() {
        return bustPortrait;
    }

    public void setBustPortrait(String bustPortrait) {
        this.bustPortrait = bustPortrait;
    }

    public String getFullPortrait() {
        return fullPortrait;
    }

    public void setFullPortrait(String fullPortrait) {
        this.fullPortrait = fullPortrait;
    }

    public String getFullPortraitV2() {
        return fullPortraitV2;
    }

    public void setFullPortraitV2(String fullPortraitV2) {
        this.fullPortraitV2 = fullPortraitV2;
    }

    public String getKillfeedPortrait() {
        return killfeedPortrait;
    }

    public void setKillfeedPortrait(String killfeedPortrait) {
        this.killfeedPortrait = killfeedPortrait;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }





    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    private List<Ability> abilities;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }
}
