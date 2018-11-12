public class Bottle {
    private int currentVolume;
    private int fullVolume;
    private int exceedVolume;
    private boolean justFilled;
    private boolean justEmptied;
    private boolean justGotTransferred;


    public Bottle(int fullVolume) {
        this.fullVolume = fullVolume;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public int getFullVolume(){
        return fullVolume;
    }

    public void fillBottle(){
        currentVolume = fullVolume;
        justFilled = true;
        justEmptied = false;
        justGotTransferred = false;
    }

    public void emptyBottle(){
        currentVolume = 0;
        justEmptied =true;
        justFilled =false;
        justGotTransferred = false;
    }


    public void getTransferredVolume(int volume){
        if(currentVolume + volume >= fullVolume ){
            exceedVolume = currentVolume +volume - fullVolume;
            currentVolume = fullVolume;
        }
        else
            currentVolume += volume;
        justGotTransferred = true;
    }

    public void transferVolume(Bottle theOtherBottle){
        theOtherBottle.getTransferredVolume(currentVolume);
        if(theOtherBottle.getOverTransferredVolume() > 0){
            currentVolume = theOtherBottle.getOverTransferredVolume();
            theOtherBottle.setOverTransferredVolume(0);
        }
        else
            currentVolume = 0;
        justFilled = false;
        justEmptied = false;
    }

    public boolean isJustFilled() {
        return justFilled;
    }

    public boolean isJustEmptied() {
        return justEmptied;
    }

    public boolean isJustGotTransferred() {
        return justGotTransferred;
    }

    public void resetBottle(){
        currentVolume = 0;
        justGotTransferred = false;
        justEmptied = false;
        justFilled = false;
    }

    public int getOverTransferredVolume() {
        return exceedVolume;
    }

    public void setOverTransferredVolume(int overTransferredVolume) {
        this.exceedVolume = overTransferredVolume;
    }
}
