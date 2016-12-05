package authority;

import java.security.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A permission that checks for bad words.
 * 注意：必须把权限类的访问范围设置为public，否则策略文件加载器寻找不到！ Created by zjh on 2016/10/23.
 */
public class WordCheckPermission extends Permission {
	private String action;

	/**
	 * Constructs a word check permission.
	 *
	 * @param targetName
	 *            a comma separated word list
	 * @param oneAction
	 *            "insert" or "avoid"
	 */
	public WordCheckPermission(String target, String oneAction) {
		super(target);
		action = oneAction;
	}

	private Set<String> getBadWordSet() {
		Set<String> set = new HashSet<>();
		set.addAll(Arrays.asList(this.getName().split(",")));
		return set;
	}

	@Override
	public String getActions() {
		return action;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}

	@Override
	public boolean equals(Object another) {
		if (another == null) {
			return false;
		}
		if (!this.getClass().equals(another.getClass())) {
			return false;
		}
		WordCheckPermission b = (WordCheckPermission) another;
		if (!Objects.equals(action, b.action)) {
			return false;
		}
		if ("insert".equals(action)) {
			return Objects.equals(this.getName(), b.getName());
		} else if ("avoid".equals(action)) {
			return this.getBadWordSet().equals(b.getBadWordSet());
		} else {
			return false;
		}
	}

	@Override
	/**
	 * 控制权限p1是否隐含p2
	 */
	public boolean implies(Permission other) {
		if (!(other instanceof WordCheckPermission)) {
			return false;
		}
		WordCheckPermission b = (WordCheckPermission) other;
		if ("insert".equals(action)) {
			return "insert".equals(b.action) && this.getName().contains(b.getName());
		} else if ("avoid".equals(action)) {
			if ("avoid".equals(b.action)) {
				return b.getBadWordSet().containsAll(this.getBadWordSet());
			} else if ("insert".equals(b.action)) {
				for (String badWord : this.getBadWordSet()) {
					if (b.getName().contains(badWord)) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}