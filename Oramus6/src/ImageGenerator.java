import java.util.*;

class ImageGenerator implements ImageGeneratorConfigurationInterface, ImageGeneratorInterface {
    private Vector<ImageGeneratorHelper> ImageGeneratorHelpersList = new Vector<>();
    private Vector<ImageGeneratorHelper> tempRepeatList = new Vector<>();
    private boolean[][] canvas;
    private int imageRow, imageColumn, commandsNumber;
    private Stack<ImageGeneratorHelper> stackHelper = new Stack<>();
    private ImageGeneratorHelper tempImageGeneratorHelper;
    private class ImageGeneratorHelper {
        private Vector<ImageGeneratorHelper> listToRepeat;
        private Vector<ImageGeneratorHelper> pointsList = new Vector<>();
        private String direction;
        private int columnFromHelper, rowFromHelper, steps, x, y;

        private ImageGeneratorHelper(int x, int y){
            this.x = x;
            this.y = y;}

        private ImageGeneratorHelper(String direction, int column, int row, int steps){
            this.direction = direction;
            this.columnFromHelper = column;
            this.rowFromHelper = row;
            this.steps = steps;}

        private ImageGeneratorHelper(int column, int row, Vector<ImageGeneratorHelper> listToRepeat){
            this.columnFromHelper = column;
            this.rowFromHelper = row;
            this.listToRepeat = listToRepeat;}

        private void repeatImageConstructor(Vector<ImageGeneratorHelper> externalPointslist) {
            int tempX = imageColumn, tempY = imageRow;
            for(int i = 0; i < steps; i++) {
                if(direction.equals("up")) tempY++;
                if(direction.equals("down")) tempY--;
                if(direction.equals("left")) tempX--;
                if(direction.equals("right")) tempX++;
                if(!canvas[tempX][tempY]) {
                    canvas[tempX][tempY] = true;
                    externalPointslist.add(new ImageGeneratorHelper(tempX, tempY));}}
            imageColumn = tempX;
            imageRow = tempY;}

        private void undoImageGeneratorHelper(){
            for(ImageGeneratorHelper point : pointsList)
                canvas[point.x][point.y] = false;
            imageColumn = columnFromHelper;
            imageRow = rowFromHelper;}

        private void redoImageGeneratorHelper(){
            for(int i = 0; i < pointsList.size(); i++)
                canvas[pointsList.get(i).x][pointsList.get(i).y] = true;
            imageColumn = columnFromHelper;
            imageRow = rowFromHelper;}

        private void repeatImageGeneratorHelper(){
            for(int i = 0; i < listToRepeat.size(); i++)
                listToRepeat.get(i).repeatImageConstructor(pointsList);}

        private void directionHelper(){
            int tempX = imageColumn, tempY = imageRow;
            for(int i = 0; i < steps; i++) {
                if(direction.equals("up")) tempY++;
                if(direction.equals("down")) tempY--;
                if(direction.equals("left")) tempX--;
                if(direction.equals("right")) tempX++;
                if(!canvas[tempX][tempY]) {
                    canvas[tempX][tempY] = true;
                    pointsList.add(new ImageGeneratorHelper(tempX, tempY));}
            }
            imageColumn = tempX;
            imageRow = tempY;
        }
    }

    public void setCanvas(boolean[][] canvas){
        this.canvas = canvas;}

    public void setInitialPosition(int col, int row){
        this.imageColumn = col;
        this.imageRow = row;
        canvas[col][row] = true;}

    public void maxUndoRedoRepeatCommands(int commands){
        this.commandsNumber = commands;}

    public void up(int steps){
        ImageGeneratorHelper ImageGeneratorHelperUp = new ImageGeneratorHelper("up", imageColumn, imageRow, steps);
        ImageGeneratorHelperUp.directionHelper();
        ImageGeneratorHelpersList.add(ImageGeneratorHelperUp);}

    public void down(int steps){
        ImageGeneratorHelper ImageGeneratorHelperDown = new ImageGeneratorHelper("down", imageColumn, imageRow, steps);
        ImageGeneratorHelperDown.directionHelper();
        ImageGeneratorHelpersList.add(ImageGeneratorHelperDown);}

    public void left(int steps){
        ImageGeneratorHelper ImageGeneratorHelperLeft = new ImageGeneratorHelper("left", imageColumn, imageRow, steps);
        ImageGeneratorHelperLeft.directionHelper();
        ImageGeneratorHelpersList.add(ImageGeneratorHelperLeft);}

    public void right(int steps){
        ImageGeneratorHelper ImageGeneratorHelperRight = new ImageGeneratorHelper("right", imageColumn, imageRow, steps);
        ImageGeneratorHelperRight.directionHelper();
        ImageGeneratorHelpersList.add(ImageGeneratorHelperRight);}

    public void repeat(int commands){
        for(int counter = commands; counter > 0; counter--)
            tempRepeatList.add(ImageGeneratorHelpersList.get(ImageGeneratorHelpersList.size()-counter));
        ImageGeneratorHelper ComplexImageGeneratorHelper = new ImageGeneratorHelper(imageColumn, imageRow, tempRepeatList);
        ComplexImageGeneratorHelper.repeatImageGeneratorHelper();
        ImageGeneratorHelpersList.add(ComplexImageGeneratorHelper); }

    public void undo(int commands) {
        for(int i = 0; i < commands; i++) {
            tempImageGeneratorHelper = ImageGeneratorHelpersList.get(ImageGeneratorHelpersList.size()-1);
            tempImageGeneratorHelper.undoImageGeneratorHelper();
            stackHelper.push(tempImageGeneratorHelper);
            ImageGeneratorHelpersList.remove(tempImageGeneratorHelper);}}

    public void redo(int commands){
        for(int i = 0; i < commands; i++){
            tempImageGeneratorHelper = stackHelper.pop();
            tempImageGeneratorHelper.redoImageGeneratorHelper();
            ImageGeneratorHelpersList.add(tempImageGeneratorHelper);}
    }
}

