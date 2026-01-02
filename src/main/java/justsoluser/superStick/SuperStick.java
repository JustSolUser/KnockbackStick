package justsoluser.superStick;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperStick extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // 이벤트를 등록합니다.
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("SuperStick On!");
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack leftItem = event.getInventory().getItem(0); // 첫 번째 슬롯
        ItemStack rightItem = event.getInventory().getItem(1); // 두 번째 슬롯 (인챈트 북)

        // 1. 아이템이 비어있는지 확인
        if (leftItem == null || rightItem == null) return;

        // 2. 왼쪽이 막대기이고 오른쪽이 인챈트 북인지 확인
        if (leftItem.getType() == Material.STICK && rightItem.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) rightItem.getItemMeta();

            // 3. 북에 '밀치기(KNOCKBACK)' 인챈트가 포함되어 있는지 확인
            if (bookMeta != null && bookMeta.hasStoredEnchant(Enchantment.KNOCKBACK)) {
                int level = bookMeta.getStoredEnchantLevel(Enchantment.KNOCKBACK);

                // 4. 결과물 아이템 복제 및 인챈트 적용
                ItemStack resultStick = leftItem.clone();
                // 'true'를 인자로 주면 해당 아이템의 일반적인 인챈트 제한을 무시하고 적용합니다.
                resultStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, level);

                // 5. 결과 슬롯에 설정
                event.setResult(resultStick);

                // 6. 경험치 비용 설정 (필요에 따라 조정 가능)
                event.getView().setRepairCost(2);
            }
        }
    }
}