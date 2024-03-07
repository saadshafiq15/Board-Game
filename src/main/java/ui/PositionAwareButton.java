package ui;

/**
 * The PositionAwareButton class extends the JButton class in Java Swing and adds functionality to
 * track and store the position of the button.
 */
public class PositionAwareButton extends javax.swing.JButton{
    private static final long serialVersionUID = 1L;
    private int yPos;
    private int xPos;

    // This means that when a `PositionAwareButton` object is created, the
    // constructor of the superclass is also called to initialize any necessary properties or perform
    // any necessary setup.
    public PositionAwareButton(){
        super();
    }
    protected PositionAwareButton(final String val){
        super(val);
    }

    protected int getAcross(){
        return xPos;
    }

    protected int getDown(){
        return yPos;
    }
    protected void setAcross(final int val){
        xPos = val;
    }
    protected void setDown(final int val){
        yPos = val;
    }

}
