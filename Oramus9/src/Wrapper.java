class Wrapper implements WrappingInterface {
    private SourceInterface sourceOfStream;
    private boolean streamEndHelper;
    private int slotsNumber;

    public void setSource(SourceInterface stream){this.sourceOfStream = stream;}
    public void setNumberOfSlots(int slots){this.slotsNumber = slots;}
    public Integer[] get() {
        int slotNumber = 0;
        Integer[] streamDataArray = new Integer[slotsNumber];
        try {
            if(streamEndHelper)
                return streamDataArray;
            for(;slotNumber < slotsNumber;slotNumber++)
                streamDataArray[slotNumber] = sourceOfStream.getValue();}
        catch(EndOfDataException e) {
                for(;slotNumber < slotsNumber;slotNumber++)
                    streamDataArray[slotNumber] = null;
                streamEndHelper = true;}
        catch (UrgentException e) {
            for(;slotNumber < slotsNumber;slotNumber++)
                streamDataArray[slotNumber] = null;}
        catch(TemporaryNoDataException e) {
            try {
                for(;slotNumber < slotsNumber;slotNumber++)
                    streamDataArray[slotNumber] = sourceOfStream.getValue();}
                    catch(EndOfDataException | TemporaryNoDataException | WaitException | UrgentException ignored) {} }
        catch(WaitException e) {
            try {
                Thread.sleep(e.doNothingTime);
                try {
                    for(;slotNumber < slotsNumber;slotNumber++)
                        streamDataArray[slotNumber] = sourceOfStream.getValue(); }
                catch(EndOfDataException | TemporaryNoDataException | WaitException | UrgentException ignored) {}}
                catch(InterruptedException ignored) {}
            }
        return streamDataArray;
    }
}