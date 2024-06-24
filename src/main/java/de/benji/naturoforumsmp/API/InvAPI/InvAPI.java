package de.benji.naturoforumsmp.API.InvAPI;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class InvAPI {
	
	public InvAPI() {
	}
	
	public ItemStack createIS(Material m) {
		return createIS(m, 1, (short) 0, null, null);
	}
	
	public ItemStack createIS(Material m, int amount) {
		return createIS(m, amount, (short) 0, null, null);
	}
	
	public ItemStack createIS(Material m, short dura) {
		return createIS(m, 1, dura, null, null);
	}
	
	public ItemStack createIS(Material m, int amount, short dura) {
		return createIS(m, amount, dura, null, null);
	}
	
	public ItemStack createIS(Material m, String display) {
		return createIS(m, 1, (short) 0, display, null);
	}
	
	public ItemStack createIS(Material m, String display, List<String> lore) {
		return createIS(m, 1, (short) 0, display, lore);
	}
	
	public ItemStack createIS(Material m, int amount, String display) {
		return createIS(m, amount, (short) 0, display, null);
	}
	
	public ItemStack createIS(Material m, short dura, String display) {
		return createIS(m, 1, dura, display, null);
	}
	
	public ItemStack createIS(Material m, int amount, String display, List<String> lore) {
		return createIS(m, amount, (short) 0, display, lore);
	}
	
	public ItemStack createIS(Material m, short dura, String display, List<String> lore) {
		return createIS(m, 1, dura, display, lore);
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack createIS(Material m, int amount, short dura, String display, List<String> lore) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta iMeta = i.getItemMeta();
		
		if(display != null) {
			iMeta.setDisplayName(display);
		}
		
		if(lore != null) {
			iMeta.setLore(lore);
		}
		
		if(dura != 0) {
			i.setDurability(dura);
		}
		
		i.setItemMeta(iMeta);
		return i;
	}
	
	public ItemStack createSkull(String url) {
		return createSkull(url, null, false, false, false);
	}

	public ItemStack createSkull(String url, ItemStack is, boolean amountover, boolean loreover, boolean displayover) {
		if (url == null || url.isEmpty())
			return null;

		GameProfile profile = new GameProfile(UUID.randomUUID(), "dev");
		byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

		return createSkull(profile, is, amountover, loreover, displayover);
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack createSkull(GameProfile profile, ItemStack is, boolean amountover, boolean loreover, boolean displayover) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        
        if(is != null) {
        	if(amountover) {
        		skull.setAmount(is.getAmount());
        	}
        	if(loreover) {
        		skullMeta.setLore(is.getItemMeta().getLore());
        	}
        	if(displayover) {
        		skullMeta.setDisplayName(is.getItemMeta().getDisplayName());
        	}
        }

        Field profileField = null;
        
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        
        profileField.setAccessible(true);
        
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        skull.setItemMeta(skullMeta);
        return skull;
    }

	public Inventory createInv(int rows, String display) {
		return createInv(rows, display, InvEnums.Empty, false, 0);
	}
	
	public Inventory createInv(int rows, String display, InvEnums invenum) {
		return createInv(rows, display, invenum, false, 0);
	}
	
	public Inventory createInv(int rows, String display, boolean closeIS, int closeISslot) {
		return createInv(rows, display, InvEnums.Empty, closeIS, closeISslot);
	}
	
	public Inventory createInv(int rows, String display, InvEnums invenum, boolean closeIS, int closeISslot) {
		Inventory i = Bukkit.createInventory(null, 9 * rows, display);

		switch(invenum) {
		case Circle:
			circlefillInv(i);
			break;
		case Full:
			fillInv(i);
			break;
		default:
			break;
		}

		if(closeIS) {
			i.setItem(closeISslot, createIS(Material.BARRIER, "§cClose"));
		}
		
		return i;
	}
	
	public void fillInv(Inventory i) {
		for(int i1 = 0; i1 <= i.getSize() - 1; i1++) {
			i.setItem(i1, createIS(Material.BLACK_STAINED_GLASS_PANE, "§0I"));
		}
	}
	
	public void circlefillInv(Inventory i) {
		ItemStack is = createIS(Material.BLACK_STAINED_GLASS_PANE, "§0I"); 
		
		for(int counter = 0; counter <= i.getSize() - 1; counter++) {
			if(counter <= 9) {
				i.setItem(counter, is);
			}
			if(counter % 9 == 0 || (counter + 1) % 9 == 0) {
				i.setItem(counter, is);
			}
			if(counter + 9 >= i.getSize()) {
				i.setItem(counter, is);
			}
		}
	}
	
	public List<Inventory> createInvPages(Inventory baseinv, String basetitle, List<ItemStack> itemstacks, int backispos, int forwardispos) {
		return createInvPages(baseinv, basetitle, itemstacks, Material.ARROW, backispos, Material.ARROW, forwardispos);
	}
	
	public List<Inventory> createInvPages(Inventory baseinv, String basetitle, List<ItemStack> itemstacks, Material backmat, int backispos, Material forwardmat, int forwardispos) {
		List<Inventory> invs = new ArrayList<>();
		int emptyspace = 0;
		
		for(int i1 = 0; i1 <= baseinv.getSize() - 1; i1++) {
			try {
				ItemStack is = baseinv.getItem(i1);
				assert is != null;
				if(is.getType().equals(Material.AIR)) {
					emptyspace++;
				}
			} catch (Exception e1) {
				emptyspace++;
			}
		}
		
		if(emptyspace != 0 && itemstacks != null) {
			if(emptyspace >= itemstacks.size()) {
				Inventory i = createInv(baseinv.getSize() / 9, basetitle);
				i.setContents(baseinv.getContents());
				
				itemstacks.forEach(i::addItem);
				
				invs.add(i);
			} else {
				int isleft = itemstacks.size();
				int isfilledin = 0;
				int page = 1;
				
				while(emptyspace < isleft) {
					Inventory i = createInv(baseinv.getSize() / 9, basetitle);
					i.setContents(baseinv.getContents());
					
					for(int i1 = 0; i1 < emptyspace; i1++) {
						i.addItem(itemstacks.get(isfilledin));
						isfilledin++;
					}
					
					invs.add(i);

					if(page - 1 > 0) {
						i.setItem(backispos, createIS(backmat, "§6Page " + (page - 1)));
					}
					i.setItem(forwardispos, createIS(forwardmat, "§6Page " + (page + 1)));
					
					page++;
					isleft = isleft - emptyspace;
				}
				
				Inventory i = createInv(baseinv.getSize() / 9, basetitle);
				i.setContents(baseinv.getContents());
				
				for(int i1 = 0; i1 < isleft; i1++) {
					i.addItem(itemstacks.get(isfilledin));
					isfilledin++;
				}
				
				i.setItem(backispos, createIS(backmat, "§6Page " + (page - 1)));
				invs.add(i);
			}
			return invs;
		} else {
			return Collections.singletonList(baseinv);
		}
	}
	
	public Inventory switchInv(List<Inventory> invs, Inventory current, int slot) {
		String display = "";
		
		try {
			display = current.getItem(slot).getItemMeta().getDisplayName();
		} catch(Exception e1) {}
		
		if(display.contains("§6Page ")) {
			display = display.replace("§6Page ", "");
			int newpage = 0;
			try {
				newpage = Integer.parseInt(display);
			} catch(Exception e1) {}

			return invs.get(newpage - 1);
		}
		return null;
	}
}
