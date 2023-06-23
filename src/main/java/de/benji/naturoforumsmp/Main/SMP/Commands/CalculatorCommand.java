package de.benji.naturoforumsmp.Main.SMP.Commands;

import de.benji.naturoforumsmp.API.MathExpression.Exception.CalculatorException;
import de.benji.naturoforumsmp.API.MathExpression.Parser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CalculatorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(args.length >= 1)) {
           sender.sendMessage("§cPlease provide a valid Expression");
           return true;
        }
        StringBuilder sb = new StringBuilder();
        Arrays.stream(args).forEach(sb::append);
        try {
            sender.sendMessage("§6The Result is: §a" + Parser.eval(sb.toString()).getValue());
        } catch (CalculatorException e) {
            sender.sendMessage("§cPlease provide a valid Expression");
        }
        return true;
    }
}
