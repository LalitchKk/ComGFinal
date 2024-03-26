import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

 class PolygonDrawing extends JPanel {
    private ArrayList<Point> dots; // list ของจุดที่ถูกคลิก
    private Color currentColor; // สีปัจจุบัน
    private JButton colorButton; // ปุ่มเลือกสี
    private JButton fillButton; // ปุ่มลงสี
    private JButton deleteButton; // ปุ่มลบรูป
    private JButton drawNewButton; // ปุ่มวาดรูปใหม่
    private boolean isFilling; // สถานะการลงสี

    public PolygonDrawing() {
        setPreferredSize(new Dimension(600, 400));
        dots = new ArrayList<>(); // list ของจุดที่ถูกคลิก
        currentColor = Color.BLACK; // defualt color
        isFilling = false; // เพื่อไม่ให้วาดแล้วลงสีเลย

        colorButton = new JButton("Change Color"); // สร้างปุ่มสำหรับเลือกสี
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(null, "Choose color", currentColor);
                if (selectedColor != null) {
                    currentColor = selectedColor;//set ค่า ถ้า selectedColor ไม่ null
                    repaint(); // วาดรูปใหม่
                }
            }
        });
        add(colorButton); // add BTN

        fillButton = new JButton("Fill");
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isFilling = true; // เมื่อคลิกปุ่มลงสี set isFilling = true
                repaint();
            }
        });
        add(fillButton); // add BTN

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dots.clear(); // เมื่อคลิกปุ่มลบ ลบจุดทั้งหมดออกจากรายการ
                repaint(); // วาดรูปใหม่เพื่อแสดงรูปที่ว่างเปล่า
            }
        });
        add(deleteButton); // add BTN

        drawNewButton = new JButton("Draw New");
        drawNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dots.clear(); // เริ่มการวาดรูปใหม่โดยการลบจุดทั้งหมด
                isFilling = false; // เปิดให้สามารถวาดรูปได้อีกครั้ง
                repaint();
            }
        });
        add(drawNewButton); // add BTN

        //if isFilling = false can  draw if not cannot draw
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) { // ถ้าคลิกเมาส์ซ้าย
                    if (!isFilling) { // ถ้ายังไม่ได้คลิกปุ่มลงสีทึบ
                        dots.add(new Point(e.getX(), e.getY())); // เมื่อคลิกที่จุดใหม่ add newdot in list 
                        repaint(); // วาดรูปใหม่
                    }
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //ลบและวาดพื้นหลัง
        Graphics2D g2d = (Graphics2D) g; // new obj Graphics2D 
        g2d.setColor(currentColor); // set color && เพื่อใช้วาดรูป

        int[] xDots = new int[dots.size()];
        int[] yDots = new int[dots.size()];
        for (int i = 0; i < dots.size(); i++) {
            xDots[i] = (int) dots.get(i).getX(); // เก็บค่า x ของจุดที่ i
            yDots[i] = (int) dots.get(i).getY(); // เก็บค่า ั ของจุดที่ i
            g2d.fillOval(xDots[i] - 3, yDots[i] - 3, 6, 6); // วาดวงกลมที่จุดที่คลิก
        }

        if (dots.size() > 1) {
            g2d.drawPolygon(xDots, yDots, dots.size());
            if (isFilling) {
                g2d.fillPolygon(xDots, yDots, dots.size());// ลงสีทึบรูป  ถ้า isFilling เป็น true
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polygon Draw T-T");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PolygonDrawing());
        frame.pack();
        frame.setVisible(true);
    }
}
