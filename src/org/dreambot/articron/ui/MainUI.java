package org.dreambot.articron.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import org.dreambot.articron.loader.HImageLoader;
import org.dreambot.articron.swing.HFrame;
import org.dreambot.articron.swing.child.HTabbedPane;
import org.dreambot.articron.ui.panels.info.InformationPanel;
import org.dreambot.articron.ui.panels.misc.MiscellaneousPanel;
import org.dreambot.articron.ui.panels.reward.RewardPanel;
import org.dreambot.articron.ui.panels.room.RoomPanel;

public class MainUI extends HFrame {
	private MiscellaneousPanel miscellaneosPanel;
	private RewardPanel rewardPanel;
	private InformationPanel informationPanel;
	private RoomPanel roomPanel;

	public MainUI(String text, BufferedImage icon) {
		super(text, icon);

		JTabbedPane tabbedPane = new HTabbedPane();
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("Info",
				informationPanel = new InformationPanel(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2), this));
		tabbedPane.addTab("Room-Spells",
				roomPanel = new RoomPanel(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2)));
		tabbedPane.addTab("Rewards",
				rewardPanel = new RewardPanel(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2)));
		tabbedPane.addTab("Miscellaneous",
				miscellaneosPanel = new MiscellaneousPanel(BorderFactory.createLineBorder(HFrame.ELEMENT_BG, 2)));

		contentPane.setPreferredSize(new Dimension(600, 350));
		prepare(null);
		miscellaneosPanel.getRoomPanel().getList().updateWidth(miscellaneosPanel.getRoomPanel().getWidth());
		rewardPanel.getDragList().updateWidth(rewardPanel.getPreferredWidth());

	}

	public MiscellaneousPanel getMiscellaneousPanel() {
		return miscellaneosPanel;
	}

	public RewardPanel getRewardPanel() {
		return rewardPanel;
	}

	public InformationPanel getInformationPanel() {
		return informationPanel;
	}

	public RoomPanel getRoomPanel() {
		return roomPanel;
	}

	public static void main(String[] args) {// coughs at HIMageloader
		new MainUI("ArtiMTA PRO", HImageLoader.loadImage("https://i.imgur.com/SGA9et4.png"));
	}
}