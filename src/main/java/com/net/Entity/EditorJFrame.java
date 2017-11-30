package com.net.Entity;
//【例6.5】  文本编辑器。
//（1）使用JTextArea，只有一种字体。
//（2） 组合框，JComboBox<String>字体，JComboBox<Integer>字号
//（3） 组合框，动作事件，没有用组合框模型，没有响应Item事件
//（4） 字号组合框排序，编辑时添加不重复数据项，二分法查找
//（5） 增加撤销和恢复操作，UndoManager类。

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.undo.UndoManager;                       //Undo/Redo管理器

public class EditorJFrame extends JFrame implements ActionListener, MouseListener {
    private JComboBox<String> combox_name;                 //字体组合框，数据项类型为String
    protected JComboBox<Integer> combox_size;              //字号组合框，数据项类型为Integer
    private JCheckBox checkbox[];                          //字形复选框数组
    private JRadioButton radiobs[];                        //颜色单选按钮数组
    protected Color colors[] = {Color.red, Color.green, Color.blue};//, Color.magenta, Color.cyan};   //颜色数组
    private String colorstr[] = {"red", "green", "blue"};//, "magenta", "cyan"};
    protected JTextArea text;                              //文本区
    protected JPopupMenu popupmenu;                        //快捷菜单
    protected JMenu menus[];                               //菜单数组
    private JCheckBoxMenuItem cbmenuitem[];                //复选菜单项数组
    private UndoManager undoManager;                       //撤销和恢复操作

    public EditorJFrame() {
        super("文本编辑器");                                    //默认BorderLayout布局
        this.setSize(800, 600);                             //设置窗口大小
        this.setLocationRelativeTo(null);                  //窗口居中
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);      //窗口关闭时，程序结束

        JToolBar toolbar = new JToolBar();                 //创建工具栏，默认水平方向
        this.getContentPane().add(toolbar, "North");        //框架内容窗格北边添加工具栏
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String fontsName[] = ge.getAvailableFontFamilyNames();//获得系统字体
        this.combox_name = new JComboBox<String>(fontsName);//组合框显示系统字体
        this.combox_name.addActionListener(this);          //组合框注册动作事件监听器
        toolbar.add(this.combox_name);
        //this.combox_name.setRenderer(new FontListRenderer());//字体列表框单元渲染器，见例12.3

        Integer sizes[] = {20, 30, 40, 50, 60, 70};               //字号
        this.combox_size = new JComboBox<Integer>(sizes);  //字号组合框
        this.combox_size.setEditable(true);                //设置组合框可编辑
        this.combox_size.addActionListener(this);          //组合框注册动作事件监听器
        toolbar.add(this.combox_size);

        String stylestr[] = {"粗体", "斜体"};                    //字形
        this.checkbox = new JCheckBox[stylestr.length];
        for (int i = 0; i < stylestr.length; i++) {
            this.checkbox[i] = new JCheckBox(stylestr[i]); //字形复选框
            toolbar.add(this.checkbox[i]);
            this.checkbox[i].addActionListener(this);      //复选框注册动作事件监听器
        }

        ButtonGroup bgroup_color = new ButtonGroup();      //按钮组
        this.radiobs = new JRadioButton[this.colorstr.length];  //颜色单选按钮数组
        for (int i = 0; i < this.radiobs.length; i++) {
            this.radiobs[i] = new JRadioButton(this.colorstr[i]); //颜色单选按钮
            this.radiobs[i].setForeground(this.colors[i]); //设置单选按钮组件的文本颜色
            this.radiobs[i].addActionListener(this);
            bgroup_color.add(this.radiobs[i]);             //单选按钮添加到按钮组
            toolbar.add(this.radiobs[i]);                  //单选按钮添加到工具栏
        }
        this.radiobs[0].setSelected(true);                 //设置单选按钮为选中状态

        JButton bopen = new JButton("打开", new ImageIcon("open.gif"));//按钮包含图标
        bopen.addActionListener(this);
        toolbar.add(bopen);                                //工具栏上添加两个按钮
        JButton bsave = new JButton("保存", new ImageIcon("save.gif"));
        bsave.addActionListener(this);
        toolbar.add(bsave);

        this.text = new JTextArea("Welcome 欢迎");
        this.text.addMouseListener(this);                  //文本区注册鼠标事件监听器
        this.getContentPane().add(new JScrollPane(this.text));  //框架内容窗格中部添加包含文本区的滚动窗格
        this.text.setForeground(colors[0]);                //设置文本区颜色
        this.addMenu();                                    //添加窗口菜单和快捷菜单
        this.combox_name.setSelectedItem("华文行楷");         //设置组合框取值，触发动作事件，执行actionPerformed()方法
//        this.combox_size.setSelectedItem(50);
//        this.checkbox[0].setSelected(true);                //设置粗体复选框选中，没有触发动作事件

        this.undoManager = new UndoManager();
        this.text.getDocument().addUndoableEditListener(this.undoManager);
        this.setVisible(true);
    }

    private void addMenu()                                 //添加窗口菜单和快捷菜单
    {
        JMenuBar menubar = new JMenuBar();                 //菜单栏
        this.setJMenuBar(menubar);                         //框架上添加菜单栏
        String menustr[] = {"文件", "编辑", "插入", "格式", "工具", "窗口", "帮助"};
        String menuitemstr[][] = {{"新建", "打开", "保存", "另存为", "|", "退出"},
                {"撤销", "恢复", "|", "剪切", "复制", "粘贴", "|", "查找", "替换"},
                {"日期", "文本"},
                {"字体"},
                {"字数统计", "自动更正", "拼写检查"}, {}, {}};
        this.menus = new JMenu[menustr.length];           //菜单数组
        JMenuItem menuitems[][] = new JMenuItem[menuitemstr.length][]; //菜单项二维数组
        for (int i = 0; i < menuitemstr.length; i++)           //添加菜单和菜单项
        {
            this.menus[i] = new JMenu(menustr[i]);         //菜单
            menubar.add(this.menus[i]);                    //菜单栏中加入菜单
            menuitems[i] = new JMenuItem[menuitemstr[i].length];
            for (int j = 0; j < menuitemstr[i].length; j++)
                if (menuitemstr[i][j].equals("|"))
                    this.menus[i].addSeparator();          //加分隔线
                else {
                    menuitems[i][j] = new JMenuItem(menuitemstr[i][j]); //创建菜单项
                    this.menus[i].add(menuitems[i][j]);    //菜单项加入到菜单
                    menuitems[i][j].addActionListener(this);//菜单项注册动作事件监听器
                }
        }
        menuitems[0][1].setIcon(new ImageIcon("open.gif"));//设置菜单项的图标
        menuitems[0][2].setIcon(new ImageIcon("save.gif"));

        JMenu menu_style = new JMenu("字形");
        menus[3].add(menu_style);                          //菜单加入到菜单中成为二级菜单
        String stylestr[] = {"粗体", "斜体"};
        this.cbmenuitem = new JCheckBoxMenuItem[stylestr.length];
        for (int i = 0; i < stylestr.length; i++) {
            this.cbmenuitem[i] = new JCheckBoxMenuItem(stylestr[i]);  //字形复选菜单项
            menu_style.add(this.cbmenuitem[i]);
            this.cbmenuitem[i].addActionListener(this);    //菜单项注册动作事件监听器
        }

        JMenu menu_color = new JMenu("颜色");
        menus[3].add(menu_color);
        ButtonGroup buttongroup = new ButtonGroup();       //按钮组
        for (int i = 0; i < this.colorstr.length; i++)         //添加单选菜单项
        {
            JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem(this.colorstr[i]);
            buttongroup.add(rbmi);                         //单选菜单项添加到按钮组
            menu_color.add(rbmi);                          //单选菜单项添加到菜单
            rbmi.setForeground(this.colors[i]);
            rbmi.addActionListener(this);
        }

        this.popupmenu = new JPopupMenu();                 //快捷菜单对象
        String menuitems_cut[] = {"剪切", "复制", "粘贴"};
        JMenuItem popmenuitem[] = new JMenuItem[menuitems_cut.length];
        for (int i = 0; i < popmenuitem.length; i++) {
            popmenuitem[i] = new JMenuItem(menuitems_cut[i]);
            this.popupmenu.add(popmenuitem[i]);            //快捷菜单加入菜单项
            popmenuitem[i].addActionListener(this);
        }
        popmenuitem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));//设置快捷键Ctrl+X
        popmenuitem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));//设置快捷键Ctrl+C
        popmenuitem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));//设置快捷键Ctrl+V
        this.text.add(this.popupmenu);                     //文本区添加快捷菜单
    }

    public void actionPerformed(ActionEvent ev)             //动作事件处理方法
    {
        System.out.println(ev.getSource().getClass().getName() + "，" +
                ev.getActionCommand());//+"，"+e.paramString());
        if (ev.getSource() instanceof JRadioButton)         //单击颜色单选按钮
            for (int i = 0; i < this.radiobs.length; i++)      //寻找当前选中的颜色单选按钮
                if (this.radiobs[i].isSelected()) {
                    this.text.setForeground(this.colors[i]);//设置文本区颜色
                    return;
                }

        if (ev.getSource() instanceof JMenuItem)            //单击菜单项
        {
            if (ev.getActionCommand().equals("退出"))
                if (JOptionPane.showConfirmDialog(this, "终止当前程序运行？", "确认", JOptionPane.YES_NO_OPTION) == 0)
                    //确认对话框，单击“是”、“否”按钮返回值分别为0、1
                    System.exit(0);                        //单击“是”按钮，结束程序运行
            if (ev.getActionCommand().equals("撤销"))
                this.undoManager.undo();
            if (ev.getActionCommand().equals("恢复"))
                this.undoManager.redo();

            if (ev.getActionCommand().equals("剪切"))
                this.text.cut();                           //将选中文本剪切送系统剪贴板
            if (ev.getActionCommand().equals("复制"))
                this.text.copy();                          //将选中文本复制送系统剪贴板
            if (ev.getActionCommand().equals("粘贴"))
                this.text.paste();                         //将剪贴板的文本粘贴在当前位置
        }

        if (ev.getSource() instanceof JComboBox<?> || ev.getSource() instanceof JCheckBox ||
                ev.getSource() instanceof JMenuItem)            //单击组合框、复选框、菜单项
        {
            int size = 0;
            try {
                String fontname = (String) this.combox_name.getSelectedItem();//获得字体名
                Object obj = this.combox_size.getSelectedItem();//获得字号组合框当前选中数据项，或输入值
                if (obj instanceof Integer)                //判断obj是否引用Integer实例
                    size = ((Integer) obj).intValue();        //获得整数值
                if (obj instanceof String)
                    size = Integer.parseInt((String) obj);  //获得字号
                if (size < 4 || size > 120)                    //字号超出指定范围时，抛出异常
                    throw new Exception("SizeException");
                java.awt.Font font = this.text.getFont();  //获得文本区的当前字体对象
                int style = font.getStyle();               //获得字形
                if (ev.getActionCommand().equals("粗体"))   //复选菜单项和复选框
                    style = style ^ 1;                     //整数的位运算，异或^
                if (ev.getActionCommand().equals("斜体"))
                    style = style ^ 2;
                this.text.setFont(new Font(fontname, style, size)); //设置文本区字体

                if (ev.getActionCommand().equals("comboBoxEdited"))  //编辑字号组合框时
                    insert(this.combox_size, size);        //将输入字号插入到组合框的数据项中
            } catch (NumberFormatException nfex)               //捕获数值格式异常
            {
                if (ev.getActionCommand().equals("comboBoxEdited"))//编辑字号组合框时
                    JOptionPane.showMessageDialog(this, "\"" + (String) this.combox_size.getSelectedItem() + "\" 不能转换成整数，请重新输入!");
            } catch (Exception ex)                            //捕获本方法抛出的异常对象
            {
                if (ev.getActionCommand().equals("comboBoxEdited") && //编辑字号组合框时
                        ex.getMessage().equals("SizeException"))
                    JOptionPane.showMessageDialog(this, size + " 字号不合适，请重新输入!");
            } finally {
            }
        }
    }

    //将value插入到组合框的数据项中，组合框数据项按T升序排序，不插入重复项，T必须实现Comparable<? super T>接口
    //采用二分法查找算法，折半插入排序的一趟。value为null时抛出空对象异常
    public <T extends Comparable<? super T>> void insert(JComboBox<T> combox, T value) {
        int begin = 0, end = combox.getItemCount() - 1, mid = end; //begin、end获得数据项序号边界
        while (begin <= end)                                 //边界有效
        {
            mid = (begin + end) / 2;                           //中间位置，当前比较元素位置
            if (value.compareTo(combox.getItemAt(mid)) == 0) //对象比较大小
                return;                                    //相同，查找成功，不插入
            if (value.compareTo(combox.getItemAt(mid)) < 0)  //给定对象小
                end = mid - 1;                               //查找范围缩小到前半段
            else begin = mid + 1;                            //查找范围缩小到后半段
        }
        combox.insertItemAt(value, begin);                 //查找不成功，将str插入在组合框的第begin项
    }

    public void mouseClicked(MouseEvent ev)                //鼠标事件处理方法，实现MouseListener接口
    {
        if (ev.getButton() == 3)                             //单击的是鼠标右键
            this.popupmenu.show(this.text, ev.getX(), ev.getY());//在鼠标单击处显示快捷菜单
    }

    public void mousePressed(MouseEvent ev) {
    }

    public void mouseReleased(MouseEvent ev) {
    }

    public void mouseEntered(MouseEvent ev) {
    }

    public void mouseExited(MouseEvent ev) {
    }

    public void mouseDragged(MouseEvent ev) {
    }

    public static void main(String arg[]) {
        new EditorJFrame();
    }
}
/*
以下未成功：
（1） 使用焦点事件，使工具栏与文本区字体同步。本例不需要，为例12.7准备。
放弃原因，因为，设置组合框某项选中，要触发动作事件，选中字体、字号将触发多次动作事件。
        public void focusGained(FocusEvent e)                  //获得焦点事件处理方法，工具栏组件显示当前内部框架的文本区字体各属性
        {
            java.awt.Font font = this.text.getFont();          //获得文本区的当前字体对象
//            this.combox_name.setSelectedItem(font.getName());  //设置字体组合框为指定字体名
//            this.combox_size.setSelectedItem(font.getSize());  //设置字号组合框为指定字号
            this.checkbox[0].setSelected(font.isBold());       //设置粗体复选框的选中与否状态
            this.checkbox[1].setSelected(font.isItalic());     //设置斜体复选框的选中与否状态
            this.cbmenuitem[0].setSelected(font.isBold());     //设置粗体复选菜单项的选中与否状态
            this.cbmenuitem[1].setSelected(font.isItalic());   //设置斜体复选菜单项的选中与否状态
        }
         public void focusLost(FocusEvent e) {}                 //失去焦点事件处理方法
（2）Action
若按钮或菜单项执行相同操作，可用Action类。例如，
        Action open = new OpenAction();
        JButton button = toolbar.add(open);       //工具栏增加按钮，没有标题，执行open对象声明的操作
        button.setIcon(new ImageIcon("open.gif"));
    public class CutAction extends AbstractAction //implements Action
    {
        public CutAction()
        {
             super();
        }
        public void actionPerformed(ActionEvent e)             //动作事件处理方法
        {
            EditorJFrame.this.text.cut();       //获得文本区的当前字体对象
        }
    }
放弃原因，操作太多，内部类太多。

*/

