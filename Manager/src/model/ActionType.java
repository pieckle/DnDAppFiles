package model;

public enum ActionType {

    Action,
    Reaction,
    Legendary;

    public static ActionType fromTag(String tag){
        for (ActionType type : ActionType.values()){
            if (type.name().equalsIgnoreCase(tag)){
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown action type " + tag);
    }

}
