package de.jxson.xpborder.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemCreator {

    private final ItemStack item;

    @Deprecated
    public ItemCreator(Material Material, Integer Amount, int sId) {
        item = new ItemStack(Material, Amount, (short) sId);
    }

    public ItemCreator(Material Material, Integer Amount) {
        item = new ItemStack(Material, Amount);
    }

    public ItemCreator setName(String Name) {
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(Name);
        item.setItemMeta(itemmeta);
        return this;
    }

    public ItemCreator setLeatherColor(Color color) {
        LeatherArmorMeta litemmeta = (LeatherArmorMeta) item.getItemMeta();
        litemmeta.setColor(color);
        item.setItemMeta(litemmeta);
        return this;
    }

    public ItemCreator addEnchantment(Enchantment Enchantment, Integer Level) {
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.addEnchant(Enchantment, Level, true);
        item.setItemMeta(itemmeta);
        return this;
    }

    public ItemCreator setLore(ArrayList<String> lore) {
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setLore(lore);
        item.setItemMeta(itemmeta);
        return this;
    }

    public ItemCreator setLore(String... lore) {
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemmeta);
        return this;
    }

    @Deprecated
    public ItemCreator setSlot(Inventory inv, String Slot) {
        inv.setItem(Integer.valueOf(Slot), item);
        return this;
    }

    public ItemCreator setSlot(Inventory inv, Integer Slot) {
        inv.setItem(Slot, item);
        return this;
    }

    public ItemCreator durability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemCreator hideFlags() {
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemmeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemmeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemmeta);
        return this;
    }

    public ItemCreator setSkullOwner(String Name) {
        SkullMeta pmeta = (SkullMeta)item.getItemMeta();
        pmeta.setOwner(Name);
        item.setItemMeta(pmeta);
        return this;
    }

    public ItemStack getItemStack() {
        return item;
    }

}
