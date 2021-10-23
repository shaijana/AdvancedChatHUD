package io.github.darkkronicle.advancedchathud.gui;

import io.github.darkkronicle.advancedchatcore.chat.AdvancedChatScreen;
import io.github.darkkronicle.advancedchatcore.interfaces.AdvancedChatScreenSection;
import io.github.darkkronicle.advancedchatcore.util.ColorUtil;
import io.github.darkkronicle.advancedchathud.AdvancedChatHud;
import io.github.darkkronicle.advancedchathud.config.HudConfigStorage;
import io.github.darkkronicle.advancedchathud.tabs.AbstractChatTab;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class HudSection extends AdvancedChatScreenSection {

    public HudSection(AdvancedChatScreen screen) {
        super(screen);
    }

    private ColorUtil.SimpleColor getColor() {
        ColorUtil.SimpleColor baseColor;
        ChatWindow sel = WindowManager.getInstance().getSelected();
        if (sel == null) {
            baseColor =
                HudConfigStorage.MAIN_TAB
                    .getInnerColor()
                    .config.getSimpleColor();
        } else {
            baseColor = sel.getTab().getInnerColor();
        }
        return baseColor;
    }

    @Override
    public void initGui() {
        int x = 2;
        int space = 2;
        int y =
            MinecraftClient.getInstance().getWindow().getScaledHeight() -
            15 -
            11;
        for (AbstractChatTab tab : AdvancedChatHud.MAIN_CHAT_TAB.getAllChatTabs()) {
            TabButton button = TabButton.fromTab(tab, x, y);
            getScreen().addButton(button, null);
            x += button.getWidth() + space;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return WindowManager
            .getInstance()
            .mouseClicked(getScreen(), mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(
        double mouseX,
        double mouseY,
        int mouseButton
    ) {
        return WindowManager
            .getInstance()
            .mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseDragged(
        double mouseX,
        double mouseY,
        int button,
        double deltaX,
        double deltaY
    ) {
        return WindowManager
            .getInstance()
            .mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (amount > 1.0D) {
            amount = 1.0D;
        }

        if (amount < -1.0D) {
            amount = -1.0D;
        }
        if (!Screen.hasShiftDown()) {
            amount *= 7.0D;
        }
        WindowManager.getInstance().scroll(amount, mouseX, mouseY);
        return true;
    }
}
