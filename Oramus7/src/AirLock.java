import java.util.*;

class Airlock implements AirlockInterface {
    private Map<State, Integer> mapWithStates = new HashMap<>();
    private State airLockState;
    private List<State> airlockStateHistory = new ArrayList<>();

    public State getState(){
        return airLockState;}
    public List<State> getHistory(){
        return airlockStateHistory;}
    public Map<State, Integer> getUsageCounters(){
        return mapWithStates;}

    public void setState(State airLockState) {
        mapWithStates.clear();
        airlockStateHistory.clear();
        this.airLockState = airLockState;
         for(State enumState : State.values())
            mapWithStates.put(enumState, 0);
         airlockStateHistory.add(airLockState);
         mapWithStates.put(airLockState, (mapWithStates.get(airLockState))+1);
    }

    public Set<State> newStates() {
        Set<State> setContainingStates = new HashSet<>();
        switch(airLockState){
            case INTERNAL_DOOR_CLOSED:
                setContainingStates.add(State.EXTERNAL_DOOR_OPENED);
                setContainingStates.add(State.EXTERNAL_DOOR_CLOSED);
                setContainingStates.add(State.INTERNAL_DOOR_OPENED);
                break;
            case EXTERNAL_DOOR_CLOSED:
                setContainingStates.add(State.EXTERNAL_DOOR_OPENED);
                setContainingStates.add(State.INTERNAL_DOOR_OPENED);
                setContainingStates.add(State.INTERNAL_DOOR_CLOSED);
                break;
            case INTERNAL_DOOR_OPENED:
                setContainingStates.add(State.DISASTER);
                setContainingStates.add(State.EXTERNAL_DOOR_CLOSED);
                setContainingStates.add(State.INTERNAL_DOOR_CLOSED);
                break;
            case EXTERNAL_DOOR_OPENED:
                setContainingStates.add(State.INTERNAL_DOOR_CLOSED);
                setContainingStates.add(State.DISASTER);
                setContainingStates.add(State.EXTERNAL_DOOR_CLOSED);
                break;
            case DISASTER:
                break;
        }
        return setContainingStates;
    }

    public void openInternalDoor() {
        switch(airLockState){
            case DISASTER:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
                break;
            case INTERNAL_DOOR_OPENED:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
                break;
            case EXTERNAL_DOOR_OPENED:
                airLockState = State.DISASTER;
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
                break;
            default:
                airLockState = State.INTERNAL_DOOR_OPENED;
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
        }
    }
    public void openExternalDoor() {
        switch(airLockState){
            case DISASTER:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState,(mapWithStates.get(airLockState))+1);
                break;
            case EXTERNAL_DOOR_OPENED:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState,(mapWithStates.get(airLockState))+1);
                break;
            case INTERNAL_DOOR_OPENED:
                airLockState = State.DISASTER;
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState,(mapWithStates.get(airLockState))+1);
                break;
            default:
                airLockState = State.EXTERNAL_DOOR_OPENED;
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState,(mapWithStates.get(airLockState))+1);
        }
    }

    public void closeInternalDoor() {
        switch(airLockState) {
            case DISASTER:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, (mapWithStates.get(airLockState))+1);
                break;
            case INTERNAL_DOOR_CLOSED:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, (mapWithStates.get(airLockState))+1);
                break;
            default:
                airLockState = State.INTERNAL_DOOR_CLOSED;
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, (mapWithStates.get(airLockState))+1);
        }
    }

    public void closeExternalDoor() {
        switch(airLockState){
            case DISASTER:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
                break;
            case EXTERNAL_DOOR_CLOSED:
                airlockStateHistory.add(airLockState);
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
                break;
            default:
                airLockState = State.EXTERNAL_DOOR_CLOSED;
                mapWithStates.put(airLockState, mapWithStates.get(airLockState) + 1);
                airlockStateHistory.add(airLockState);
                break;
        }
    }


}