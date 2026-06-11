package com.wavesurvival.mod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.wavesurvival.mod.network.NetworkHandler;
import com.wavesurvival.mod.network.packets.BuyItemPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen extends AbstractContainerScreen<ShopMenu> {

    private static final ResourceLocation BG = new ResourceLocation("wavesurvival", "textures/gui/shop_bg.png");
    private final List<ShopItem> shopItems = new ArrayList<>();
    private int scrollOffset = 0;
    private static final int VISIBLE_ROWS = 4;
    private static final int COLS = 5;

    public ShopScreen(ShopMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 280;
        this.imageHeight = 220;
        buildShopItems();
    }

    private void buildShopItems() {
        // ===== WEAPONS =====
        shopItems.add(new ShopItem(new ItemStack(Items.WOODEN_SWORD),   "Дерев. меч",    10));
        shopItems.add(new ShopItem(new ItemStack(Items.STONE_SWORD),    "Камен. меч",    30));
        shopItems.add(new ShopItem(new ItemStack(Items.IRON_SWORD),     "Желез. меч",    80));
        shopItems.add(new ShopItem(new ItemStack(Items.DIAMOND_SWORD),  "Алмазный меч",  200));
        shopItems.add(new ShopItem(new ItemStack(Items.NETHERITE_SWORD),"Незер. меч",    500));

        // ===== BOWS & RANGED =====
        shopItems.add(new ShopItem(new ItemStack(Items.BOW),            "Лук",           50));
        shopItems.add(new ShopItem(new ItemStack(Items.CROSSBOW),       "Арбалет",       90));
        shopItems.add(new ShopItem(new ItemStack(Items.ARROW, 32),      "32 стрелы",     20));
        shopItems.add(new ShopItem(new ItemStack(Items.SPECTRAL_ARROW, 16), "Спект. стрелы", 40));

        // ===== ARMOR =====
        shopItems.add(new ShopItem(new ItemStack(Items.LEATHER_CHESTPLATE), "Кожа нагруд.", 40));
        shopItems.add(new ShopItem(new ItemStack(Items.IRON_CHESTPLATE),    "Жел. нагруд.", 120));
        shopItems.add(new ShopItem(new ItemStack(Items.DIAMOND_CHESTPLATE), "Алм. нагруд.", 350));
        shopItems.add(new ShopItem(new ItemStack(Items.IRON_HELMET),        "Жел. шлем",   80));
        shopItems.add(new ShopItem(new ItemStack(Items.DIAMOND_HELMET),     "Алм. шлем",   220));
        shopItems.add(new ShopItem(new ItemStack(Items.IRON_LEGGINGS),      "Жел. штаны",  100));
        shopItems.add(new ShopItem(new ItemStack(Items.DIAMOND_LEGGINGS),   "Алм. штаны",  280));
        shopItems.add(new ShopItem(new ItemStack(Items.IRON_BOOTS),         "Жел. ботинки",60));
        shopItems.add(new ShopItem(new ItemStack(Items.DIAMOND_BOOTS),      "Алм. ботинки",180));

        // ===== FOOD =====
        shopItems.add(new ShopItem(new ItemStack(Items.BREAD, 8),       "Хлеб x8",       15));
        shopItems.add(new ShopItem(new ItemStack(Items.COOKED_BEEF, 8), "Стейк x8",      35));
        shopItems.add(new ShopItem(new ItemStack(Items.GOLDEN_APPLE),   "Золотое яблоко",120));
        shopItems.add(new ShopItem(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), "Нотч яблоко", 800));

        // ===== POTIONS =====
        shopItems.add(new ShopItem(new ItemStack(Items.POTION),         "Зелье силы",    60,  "strength"));
        shopItems.add(new ShopItem(new ItemStack(Items.POTION),         "Зелье скорости",50,  "speed"));
        shopItems.add(new ShopItem(new ItemStack(Items.POTION),         "Зелье здоровья",45,  "healing"));
        shopItems.add(new ShopItem(new ItemStack(Items.POTION),         "Зелье защиты",  70,  "resistance"));

        // ===== SPECIAL =====
        shopItems.add(new ShopItem(new ItemStack(Items.TOTEM_OF_UNDYING), "Тотем бессмертия", 1000));
        shopItems.add(new ShopItem(new ItemStack(Items.SHIELD),           "Щит",              80));
        shopItems.add(new ShopItem(new ItemStack(Items.OBSIDIAN, 16),     "Обсидиан x16",     60));
        shopItems.add(new ShopItem(new ItemStack(Items.TNT, 4),           "ТНТ x4",           100));
        shopItems.add(new ShopItem(new ItemStack(Items.ENDER_PEARL, 4),   "Эндер-жемчуг x4", 80));

        // ===== GOLEMS =====
        shopItems.add(new ShopItem(new ItemStack(Items.CARVED_PUMPKIN),   "Голем (призыв)",  150, "golem"));
        shopItems.add(new ShopItem(new ItemStack(Items.IRON_BLOCK),       "Мега-голем",      500, "mega_golem"));
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Draw dark background
        g.fill(x, y, x + imageWidth, y + imageHeight, 0xDD000000);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, 0xFF1A1A2E);

        // Header
        g.fill(x, y, x + imageWidth, y + 20, 0xFF16213E);
        g.drawString(font, "§6⚔ МАГАЗИН ⚔", x + imageWidth/2 - 35, y + 6, 0xFFD700);

        // Draw item grid
        int startIdx = scrollOffset * COLS;
        for (int i = 0; i < VISIBLE_ROWS * COLS; i++) {
            int idx = startIdx + i;
            if (idx >= shopItems.size()) break;

            ShopItem item = shopItems.get(idx);
            int col = i % COLS;
            int row = i / COLS;
            int ix = x + 10 + col * 52;
            int iy = y + 25 + row * 47;

            // Cell background
            boolean hover = mouseX >= ix && mouseX < ix + 48 && mouseY >= iy && mouseY < iy + 43;
            g.fill(ix, iy, ix + 48, iy + 43, hover ? 0xFF2D4A6E : 0xFF0F3460);
            g.fill(ix + 1, iy + 1, ix + 47, iy + 42, hover ? 0xFF3A5F8A : 0xFF16213E);

            // Item icon
            g.renderItem(item.stack, ix + 15, iy + 5);

            // Name
            String name = item.name.length() > 10 ? item.name.substring(0, 9) + "." : item.name;
            g.drawString(font, "§e" + name, ix + 2, iy + 25, 0xFFFFFF, false);

            // Price
            g.drawString(font, "§6" + item.price + "м", ix + 2, iy + 33, 0xFFFFFF, false);
        }

        // Scroll arrows
        g.fill(x + imageWidth - 20, y + 25, x + imageWidth - 5, y + 40, 0xFF0F3460);
        g.drawString(font, "▲", x + imageWidth - 17, y + 28, 0xFFFFFF);
        g.fill(x + imageWidth - 20, y + imageHeight - 40, x + imageWidth - 5, y + imageHeight - 25, 0xFF0F3460);
        g.drawString(font, "▼", x + imageWidth - 17, y + imageHeight - 37, 0xFFFFFF);

        // Balance display bottom
        int coins = ClientWaveData.getCoins();
        g.fill(x, y + imageHeight - 20, x + imageWidth, y + imageHeight, 0xFF16213E);
        g.drawString(font, "§6Монеты: " + coins, x + 5, y + imageHeight - 14, 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Scroll up
        if (mx >= x + imageWidth - 20 && mx < x + imageWidth - 5 && my >= y + 25 && my < y + 40) {
            if (scrollOffset > 0) scrollOffset--;
            return true;
        }
        // Scroll down
        int maxScroll = (shopItems.size() + COLS - 1) / COLS - VISIBLE_ROWS;
        if (mx >= x + imageWidth - 20 && mx < x + imageWidth - 5 && my >= y + imageHeight - 40 && my < y + imageHeight - 25) {
            if (scrollOffset < maxScroll) scrollOffset++;
            return true;
        }

        // Item click
        int startIdx = scrollOffset * COLS;
        for (int i = 0; i < VISIBLE_ROWS * COLS; i++) {
            int idx = startIdx + i;
            if (idx >= shopItems.size()) break;
            int col = i % COLS;
            int row = i / COLS;
            int ix = x + 10 + col * 52;
            int iy = y + 25 + row * 47;
            if (mx >= ix && mx < ix + 48 && my >= iy && my < iy + 43) {
                NetworkHandler.CHANNEL.sendToServer(new BuyItemPacket(idx));
                return true;
            }
        }

        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        int maxScroll = (shopItems.size() + COLS - 1) / COLS - VISIBLE_ROWS;
        if (delta < 0 && scrollOffset < maxScroll) scrollOffset++;
        if (delta > 0 && scrollOffset > 0) scrollOffset--;
        return true;
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        // Suppress default labels
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        renderBackground(g);
        super.render(g, mouseX, mouseY, delta);
    }

    // Inner class for shop items
    public static class ShopItem {
        public final ItemStack stack;
        public final String name;
        public final int price;
        public final String specialId;

        public ShopItem(ItemStack stack, String name, int price) {
            this(stack, name, price, null);
        }

        public ShopItem(ItemStack stack, String name, int price, String specialId) {
            this.stack = stack;
            this.name = name;
            this.price = price;
            this.specialId = specialId;
        }
    }
}
